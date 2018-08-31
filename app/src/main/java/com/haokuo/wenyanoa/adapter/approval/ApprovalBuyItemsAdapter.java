package com.haokuo.wenyanoa.adapter.approval;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.approval.ApproveBuyItemsResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalBuyItemsAdapter extends BaseQuickAdapter<ApproveBuyItemsResultBean.BuyItemsBean, BaseViewHolder> {

    public ApprovalBuyItemsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApproveBuyItemsResultBean.BuyItemsBean item) {
        helper.setText(R.id.tv_name, String.format("%s的物品申购", item.getRealname()));
        helper.setText(R.id.tv_apply_time, String.format("申请时间：%s", item.getFillformDate()));
        helper.setText(R.id.tv_goods_name, String.format("申购物品：%s", item.getBuyItems()));
        helper.setText(R.id.tv_approve_state, item.getAppStatus());
        helper.setText(R.id.tv_fill_form_time, item.getFillformDate());
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext, item.getNowPhoto(), ivAvatar, item.getNowSex());
    }
}
