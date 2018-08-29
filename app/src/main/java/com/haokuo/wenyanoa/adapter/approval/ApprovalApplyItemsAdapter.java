package com.haokuo.wenyanoa.adapter.approval;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveApplyItemsResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalApplyItemsAdapter extends BaseQuickAdapter<ApproveApplyItemsResultBean.ApplyItemsBean, BaseViewHolder> {

    public ApprovalApplyItemsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveApplyItemsResultBean.ApplyItemsBean item) {
        helper.setText(R.id.tv_name, String.format("%s的物品领用", item.getRealname()));
        helper.setText(R.id.tv_item_name, String.format("物品名称：%s", item.getItems_name()));
        helper.setText(R.id.tv_incident, String.format("物品用途：%s", item.getIncident()));
        helper.setText(R.id.tv_approve_state, item.getAppStatus());
        helper.setText(R.id.tv_fill_form_time, item.getFillformDate());
    }
}
