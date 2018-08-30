package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApproverAdapter extends BaseQuickAdapter<StaffBean, BaseViewHolder> {

    public ApproverAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, StaffBean item) {
        helper.setGone(R.id.iv_approver_arrow, helper.getLayoutPosition() != mData.size() - 1);
        helper.setText(R.id.tv_approver_name, item.getName());
        ImageView ivApproverAvatar = helper.getView(R.id.iv_approver_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext,item.getAvatar(),ivApproverAvatar,item.getSex());
    }
}
