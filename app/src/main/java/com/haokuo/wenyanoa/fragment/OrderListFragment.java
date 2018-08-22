package com.haokuo.wenyanoa.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.OrderListAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */
public class OrderListFragment extends BaseLazyLoadFragment {
    @BindView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @BindView(R.id.srl_order_list)
    SmartRefreshLayout mSrlOrderList;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void initData() {
        mRvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        OrderListAdapter orderListAdapter = new OrderListAdapter(R.layout.item_order_list);
        mRvOrderList.setAdapter(orderListAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        orderListAdapter.setNewData(dishesBeans);
    }

    @Override
    protected void initListener() {

    }
}
