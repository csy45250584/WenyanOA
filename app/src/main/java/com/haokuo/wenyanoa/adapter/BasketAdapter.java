package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.BasketListResultBean;

import java.math.BigDecimal;
import java.text.NumberFormat;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class BasketAdapter extends BaseQuickAdapter<BasketListResultBean.BasketBean, BaseViewHolder> {

    public BasketAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, BasketListResultBean.BasketBean item) {
        //设置选中框
        SmoothCheckBox cbDish = helper.getView(R.id.cb_dish);
        cbDish.setChecked(item.isChecked());
        cbDish.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                mData.get(helper.getLayoutPosition()).setChecked(isChecked);
            }
        });
        helper.addOnClickListener(R.id.tv_delete_dish);
//        //设置
        ImageView ivDishImage = helper.getView(R.id.iv_dish_image);
        Glide.with(mContext).load(item.getCoverImage()).into(ivDishImage);
        helper.setText(R.id.tv_dish_name, item.getFoodName());
        helper.setText(R.id.tv_dining_time, String.format("用餐时间:%s", item.getEatTime()));
        helper.setText(R.id.tv_dish_count, String.format("数量:%d", item.getNum()));
        BigDecimal money = BigDecimal.valueOf(item.getFoodPrice());
        BigDecimal totalPrice = money.multiply(BigDecimal.valueOf(item.getNum()));
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String totalPriceString = currencyFormatter.format(totalPrice);
        helper.setText(R.id.tv_dish_total_price, String.format("总价:%s", totalPriceString));
    }

    //    public void removeItem(String selectedDate, int id) {
    //        for (int i = 0; i < mData.size(); i++) {
    //            BasketDishesBean bean = mData.get(i);
    //            if (((int) bean.getId()) == id || selectedDate.equals(bean.getDiningDate())) {
    //                remove(i);
    //            }
    //        }
    //    }
}
