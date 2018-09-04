package com.haokuo.wenyanoa.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.OrderListResultBean;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class FoodListAdapter extends BaseQuickAdapter<OrderListResultBean.DishBean, BaseViewHolder> {

    public FoodListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final OrderListResultBean.DishBean item) {
        helper.setText(R.id.tv_food_name, item.getFoodName());
        helper.setText(R.id.tv_food_count, String.format("X%d", item.getFoodName()));
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        String foodPrice = currencyInstance.format(BigDecimal.valueOf(item.getFoodPrice()));
        helper.setText(R.id.tv_dish_price, foodPrice);
    }
}
