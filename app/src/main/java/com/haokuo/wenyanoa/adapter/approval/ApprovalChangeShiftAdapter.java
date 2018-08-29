package com.haokuo.wenyanoa.adapter.approval;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveChangeShiftResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalChangeShiftAdapter extends BaseQuickAdapter<ApproveChangeShiftResultBean.ChangeShiftBean, BaseViewHolder> {

    public ApprovalChangeShiftAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveChangeShiftResultBean.ChangeShiftBean item) {
        helper.setText(R.id.tv_name, String.format("%s的调班", item.getRealname()));
        helper.setText(R.id.tv_original_time, String.format("开始时间：%s", item.getOldWorkDate()));
        helper.setText(R.id.tv_target_time, String.format("调至时间：%s", item.getNewWorkDate()));
        helper.setText(R.id.tv_approve_state, item.getStateString());
        helper.setText(R.id.tv_fill_form_time, item.getCreateDate());
    }
}
