package com.haokuo.wenyanoa.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;

import java.util.List;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class SelectDayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectDayAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_weekday, item);
    }
}
