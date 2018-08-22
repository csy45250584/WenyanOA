package com.haokuo.wenyanoa.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ApplicationBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class MainApplicationAdapter extends BaseQuickAdapter<ApplicationBean, BaseViewHolder> {

    public MainApplicationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplicationBean item) {
        helper.setText(R.id.tv_app_title, item.getTitle());
        helper.setImageResource(R.id.iv_app_icon, item.getIconSrc());
    }
}
