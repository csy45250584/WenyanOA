package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.DishesBean;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class DishesAdapter extends BaseQuickAdapter<DishesBean, BaseViewHolder> {

    public DishesAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DishesBean item) {
        ImageView ivDishImage = helper.getView(R.id.iv_dish_image);
        Glide.with(mContext).load(item.getImageUrl()).into(ivDishImage);
        helper.setText(R.id.tv_dish_name, item.getName());
        //设置价格
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        String format = currencyInstance.format(new BigDecimal(item.getPrice()));
        helper.setText(R.id.tv_dish_price, format);
        int count = item.getCount();
        helper.setText(R.id.tv_dish_count, String.valueOf(count));
        helper.setGone(R.id.iv_delete_dish, count != 0);
        helper.setGone(R.id.tv_dish_count, count != 0);
        helper.addOnClickListener(R.id.iv_delete_dish);
        helper.addOnClickListener(R.id.iv_add_dish);
    }

    public void changeCount(int position, boolean isAdd) {
        DishesBean dishesBean = mData.get(position);
        if (isAdd) {
            dishesBean.setCount(dishesBean.getCount() + 1);
        } else {
            dishesBean.setCount(dishesBean.getCount() - 1);
        }
        notifyItemChanged(position);
    }
}
