package com.haokuo.wenyanoa.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.BasketAdapter;
import com.haokuo.wenyanoa.bean.BasketDishesBean;
import com.haokuo.wenyanoa.bean.BasketListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.eventbus.DishRefreshEvent;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.IdParams;
import com.haokuo.wenyanoa.network.bean.base.PageParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-08.
 */

public class BasketFragment extends BaseLazyLoadFragment {
    private static final int PAGE_SIZE = 10;
    @BindView(R.id.rv_basket)
    RecyclerView mRvBasket;
    @BindView(R.id.srl_basket)
    SmartRefreshLayout mSrlBasket;
    private ArrayList<BasketDishesBean> mBasketDishList;
    private BasketAdapter mBasketAdapter;
    private PageParams mParams;
    private boolean hasNewData;
    private UserInfoBean mUserInfo;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_basket;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        Resources resources = getResources();
        mRvBasket.setLayoutManager(new LinearLayoutManager(mContext));
        int dividerPadding = (int) (resources.getDimension(R.dimen.dp_16) + 0.5f);
        int dividerHeight = (int) (resources.getDimension(R.dimen.dp_1) + 0.5f);
        mRvBasket.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                dividerPadding, dividerPadding));
        mBasketAdapter = new BasketAdapter(R.layout.item_basket);
        mRvBasket.setAdapter(mBasketAdapter);
        //        mBasketDishList = new ArrayList<>();
        //        mBasketAdapter.setNewData(mBasketDishList);
        mUserInfo = OaSpUtil.getUserInfo();
        mParams = new PageParams(mUserInfo.getUserId(), mUserInfo.getApikey(), 0, PAGE_SIZE);
    }




    //    @Subscribe
    //    public void onDishClickEvent(DishClickEvent event) {
    //        String selectedDate = event.getSelectedDate();
    //        GetFoodListResultBean.DishesBean dishesBean = event.getDishesBean();
    //        int weekday = event.getWeekday();
    ////        new BasketDishesBean(dishesBean.getFoodName(), dishesBean.getCoverImage(), dishesBean.getFoodPrice()*dishesBean.getCount(),
    ////                )
    //        //显示
    //        int count = dishesBean.getCount();compile
    //        if (count == 0) {
    //            int deleteCount = LitePal.deleteAll(BasketDishesBean.class, "dishId = ? AND diningDate = ?", String.valueOf(dishesBean.getId()), selectedDate);
    //            if (deleteCount > 0) {
    //                mBasketAdapter.removeItem(selectedDate, dishesBean.getId());
    //            }
    //        } else {
    //
    ////            LitePal.updateAll(BasketDishesBean.class,,"dishId = ? AND diningDate = ?", String.valueOf(dishesBean.getId()), selectedDate)
    ////            mBasketAdapter.updateItem(selectedDate, dishesBean.getId());
    //        }
    //        //更新数据库
    //
    //    }

    @Override
    protected void loadData() {
        mSrlBasket.autoRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && hasNewData) {
            mSrlBasket.autoRefresh();
        }
    }

    @Subscribe
    public void onDishRefreshEvent(DishRefreshEvent event) {
        hasNewData = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initListener() {
        mSrlBasket.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                HttpHelper.getInstance().getBasketList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        BasketListResultBean resultBean = JSON.parseObject(json, BasketListResultBean.class);
                        List<BasketListResultBean.BasketBean> data = resultBean.getData();
                        mBasketAdapter.addData(data);
                        if (mBasketAdapter.getData().size() == resultBean.getCount()) {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            refreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取菜篮列表失败，" + message);
                        refreshLayout.finishLoadMore(false);
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                HttpHelper.getInstance().getBasketList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        BasketListResultBean resultBean = JSON.parseObject(json, BasketListResultBean.class);
                        List<BasketListResultBean.BasketBean> data = resultBean.getData();
                        mBasketAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(mBasketAdapter.getData().size() == resultBean.getCount());
                        hasNewData = false;
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取菜篮列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });

        mBasketAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_delete_dish:
                        IdParams params = new IdParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mBasketAdapter.getItem(position).getId());
                        HttpHelper.getInstance().deleteItemById(params, new NetworkCallback() {
                            @Override
                            public void onSuccess(Call call, String json) {
                                mBasketAdapter.remove(position);
                            }

                            @Override
                            public void onFailure(Call call, String message) {
                                ToastUtils.showShort("删除失败，" + message);
                            }
                        });
                        break;
                }
            }
        });
    }

    public List<BasketListResultBean.BasketBean> getBasketList() {
        if (mBasketAdapter != null) {
            return mBasketAdapter.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void refreshList() {
        loadData();
    }
}
