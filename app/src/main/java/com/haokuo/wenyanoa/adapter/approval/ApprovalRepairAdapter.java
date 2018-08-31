package com.haokuo.wenyanoa.adapter.approval;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveRepairResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalRepairAdapter extends BaseQuickAdapter<ApproveRepairResultBean.RepairBean, BaseViewHolder> {

    public ApprovalRepairAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveRepairResultBean.RepairBean item) {
        helper.setText(R.id.tv_name, String.format("%s的报修", item.getRealname()));
        helper.setText(R.id.tv_repair_time, String.format("期望时间：%s", item.getExpectfixDate()));
        helper.setText(R.id.tv_repair_item, String.format("维修物品：%s", item.getFixItems()));
        helper.setText(R.id.tv_approve_state, item.getAppStatus());
        helper.setText(R.id.tv_fill_form_time, item.getFillformDate());
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext, item.getNowPhoto(), ivAvatar, item.getNowSex());
    }
}
