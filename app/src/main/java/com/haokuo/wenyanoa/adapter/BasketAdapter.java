package com.haokuo.wenyanoa.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.BasketDishesBean;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class BasketAdapter extends BaseQuickAdapter<BasketDishesBean, BaseViewHolder> {

    public BasketAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, BasketDishesBean item) {
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

    public void removeItem(String selectedDate, int id) {
        for (int i = 0; i < mData.size(); i++) {
            BasketDishesBean bean = mData.get(i);
            if (((int) bean.getId()) == id || selectedDate.equals(bean.getDiningDate())) {
                remove(i);
            }
        }
    }
}
