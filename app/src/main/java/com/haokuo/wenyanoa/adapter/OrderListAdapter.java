package com.haokuo.wenyanoa.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.OrderListResultBean;

import java.util.List;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListResultBean.OrderBean, BaseViewHolder> {

    public OrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final OrderListResultBean.OrderBean item) {
        helper.setText(R.id.tv_name, String.format("%s的订单", item.getRealname()));
        helper.setText(R.id.tv_order_date, item.getApplicationTime());
        helper.setText(R.id.tv_eat_date, item.getEatTime());
        helper.setText(R.id.tv_tel, item.getTelPhone());
        helper.setText(R.id.tv_pay_method, item.getPayMethod());
        helper.setText(R.id.tv_total_price, item.getTotalPrice());

        RecyclerView rvFoodList = helper.getView(R.id.rv_food_list);
        rvFoodList.setLayoutManager(new LinearLayoutManager(mContext));
        FoodListAdapter foodListAdapter = new FoodListAdapter(R.layout.item_food_list);
        rvFoodList.setAdapter(foodListAdapter);
        List<OrderListResultBean.DishBean> dishes = item.getDishes();
        foodListAdapter.setNewData(dishes);
    }
}
