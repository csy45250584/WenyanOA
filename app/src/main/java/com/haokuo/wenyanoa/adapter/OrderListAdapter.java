package com.haokuo.wenyanoa.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.DishesBean;

import java.util.ArrayList;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class OrderListAdapter extends BaseQuickAdapter<DishesBean, BaseViewHolder> {

    public OrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DishesBean item) {
        RecyclerView rvFoodList = helper.getView(R.id.rv_food_list);
        rvFoodList.setLayoutManager(new LinearLayoutManager(mContext));
        FoodListAdapter foodListAdapter = new FoodListAdapter(R.layout.item_food_list);
        rvFoodList.setAdapter(foodListAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        foodListAdapter.setNewData(dishesBeans);
    }
}
