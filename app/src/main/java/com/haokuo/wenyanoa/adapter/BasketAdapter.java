package com.haokuo.wenyanoa.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.DishesBean;

import java.math.BigDecimal;
import java.text.NumberFormat;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class BasketAdapter extends BaseQuickAdapter<DishesBean, BaseViewHolder> {

    public BasketAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DishesBean item) {
        ImageView ivDishImage = helper.getView(R.id.iv_dish_image);
        Glide.with(mContext).load(item.getImageUrl()).into(ivDishImage);
        helper.setText(R.id.tv_dish_name, item.getName() + "fdfdfdfdfdf");
        helper.setText(R.id.tv_dining_time, String.format("用餐时间：%s", item.getTime()));
        helper.setText(R.id.tv_dish_count, String.format("数量：%d", item.getCount()));
        BigDecimal price = new BigDecimal(item.getPrice());
        BigDecimal count = new BigDecimal(item.getCount());
        BigDecimal totalPrice = price.multiply(count);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String priceString = currencyFormatter.format(totalPrice);
        helper.setText(R.id.tv_dish_count, String.format("总价：%s", priceString));
        //设置选中框
        SmoothCheckBox cbDish = helper.getView(R.id.cb_dish);
        cbDish.setChecked(item.isChecked());
        cbDish.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                mData.get(helper.getLayoutPosition()).setChecked(isChecked);
            }
        });
        //设置
        helper.getView(R.id.tv_delete_dish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(helper.getLayoutPosition());
            }
        });
    }
}
