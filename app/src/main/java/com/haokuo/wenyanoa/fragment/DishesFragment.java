package com.haokuo.wenyanoa.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.DishesAdapter;
import com.haokuo.wenyanoa.bean.GetFoodListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.eventbus.WeekdaySelectedEvent;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.GetInFoodListParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-08.
 */

public class DishesFragment extends BaseLazyLoadFragment {
    private static final String TYPE = "type";
    public static final int TYPE_BREAKFAST = 0;
    public static final int TYPE_LAUNCH = 1;
    public static final int TYPE_DINNER = 2;
    @BindView(R.id.rv_dishes)
    RecyclerView mRvDishes;
    @BindView(R.id.srl_dishes)
    SmartRefreshLayout mSrlDishes;
    private DishesAdapter mDishesAdapter;
    private int mWeekday;
    private UserInfoBean mUserInfo;
    private int mType;

    public static DishesFragment newInstance(int type) {
        DishesFragment frag = new DishesFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        frag.setArguments(args);
        return frag;
    }

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_dishes;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(TYPE);
        }
        EventBus.getDefault().register(this);
        Resources resources = getResources();
        mRvDishes.setLayoutManager(new LinearLayoutManager(mContext));
        int dividerPadding = (int) (resources.getDimension(R.dimen.dp_16) + 0.5f);
        int dividerHeight = (int) (resources.getDimension(R.dimen.dp_1) + 0.5f);
        mRvDishes.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                dividerPadding, dividerPadding));
        mDishesAdapter = new DishesAdapter(R.layout.item_dishes);
        mRvDishes.setAdapter(mDishesAdapter);
        mSrlDishes.setEnableLoadMore(false);
        mUserInfo = OaSpUtil.getUserInfo();
    }

    @Override
    protected void initListener() {
        mDishesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete_dish:
                        mDishesAdapter.changeCount(position, false);
                        break;
                    case R.id.iv_add_dish:
                        mDishesAdapter.changeCount(position, true);
                        break;
                }
            }
        });
        mSrlDishes.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                GetInFoodListParams params = new GetInFoodListParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mWeekday, mType);
                HttpHelper.getInstance().getFoodList(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        List<GetFoodListResultBean.DishesBean> data = JSON.parseObject(json, GetFoodListResultBean.class).getData();
                        mDishesAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取菜品列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
    }

    @Subscribe(priority = 1)
    public void onWeekdaySelected(WeekdaySelectedEvent event) {
        mWeekday = event.getWeekday();
        mDishesAdapter.setNewData(null);
        resetLoadData();
    }

    @Override
    protected void loadData() {
        mSrlDishes.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
