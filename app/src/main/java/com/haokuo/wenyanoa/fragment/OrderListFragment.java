package com.haokuo.wenyanoa.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.OrderListAdapter;
import com.haokuo.wenyanoa.bean.OrderListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.PageWithTimeParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-08.
 */
public class OrderListFragment extends BaseLazyLoadFragment {
    private static final int PAGE_SIZE = 10;
    @BindView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @BindView(R.id.srl_order_list)
    SmartRefreshLayout mSrlOrderList;
    private PageWithTimeParams mParams;
    private OrderListAdapter mOrderListAdapter;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void initData() {
        mRvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderListAdapter = new OrderListAdapter(R.layout.item_order_list);
        mRvOrderList.setAdapter(mOrderListAdapter);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        Calendar calendar = Calendar.getInstance();
        String endTime = TimeUtils.date2String(calendar.getTime(), TimeUtils.CUSTOM_FORMAT);
        calendar.add(Calendar.MONTH, -1);
        String startTime = TimeUtils.date2String(calendar.getTime(), TimeUtils.CUSTOM_FORMAT);
        mParams = new PageWithTimeParams(userInfo.getUserId(), userInfo.getApikey(), 0, PAGE_SIZE, startTime, endTime);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mSrlOrderList.autoRefresh();
    }

    @Override
    protected void initListener() {
        mSrlOrderList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                HttpHelper.getInstance().getBasketList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        OrderListResultBean resultBean = JSON.parseObject(json, OrderListResultBean.class);
                        List<OrderListResultBean.OrderBean> data = resultBean.getData();
                        mOrderListAdapter.addData(data);
                        if (mOrderListAdapter.getData().size() == resultBean.getCount()) {
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
                        OrderListResultBean resultBean = JSON.parseObject(json, OrderListResultBean.class);
                        List<OrderListResultBean.OrderBean> data = resultBean.getData();
                        mOrderListAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(mOrderListAdapter.getData().size() == resultBean.getCount());
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取菜篮列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
    }
}
