package com.haokuo.wenyanoa.adapter.approval;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveLeaveResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalLeaveAdapter extends BaseQuickAdapter<ApproveLeaveResultBean.LeaveBean, BaseViewHolder> {

    public ApprovalLeaveAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveLeaveResultBean.LeaveBean item) {
        helper.setText(R.id.tv_name, String.format("%s的请假", item.getRealname()));
        helper.setText(R.id.tv_begin_time, String.format("开始时间：%s", item.getStartDate()));
        helper.setText(R.id.tv_end_time, String.format("结束时间：%s", item.getEndDate()));
        helper.setText(R.id.tv_approve_state, item.getAppStatus());
        helper.setText(R.id.tv_fill_form_time, item.getFillformDate());
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext,item.getUserInfo().getHeadPhoto(),ivAvatar,item.getUser().getSex());

    }
}
