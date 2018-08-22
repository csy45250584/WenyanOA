package com.haokuo.wenyanoa.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ApplicationBean;

import java.util.List;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalSubjectAdapter extends BaseQuickAdapter<ApplicationBean, BaseViewHolder> {

    public ApprovalSubjectAdapter(int layoutResId, @Nullable List<ApplicationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ApplicationBean item) {
        helper.setText(R.id.tv_app_title, item.getTitle());
        helper.setImageResource(R.id.iv_app_icon, item.getIconSrc());
    }
}
