package com.haokuo.wenyanoa.adapter.approval;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveTripResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalTripAdapter extends BaseQuickAdapter<ApproveTripResultBean.TripBean, BaseViewHolder> {

    public ApprovalTripAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveTripResultBean.TripBean item) {
        helper.setText(R.id.tv_name, String.format("%s的公差", item.getRealname()));
        helper.setText(R.id.tv_begin_time, String.format("开始时间：%s", item.getStartDate()));
        helper.setText(R.id.tv_end_time, String.format("结束时间：%s", item.getEndDate()));
        helper.setText(R.id.tv_approve_state, item.getAppStatus());
        helper.setText(R.id.tv_fill_form_time, item.getFillformDate());
    }
}
