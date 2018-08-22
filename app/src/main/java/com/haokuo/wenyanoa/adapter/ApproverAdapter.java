package com.haokuo.wenyanoa.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.DishesBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApproverAdapter extends BaseQuickAdapter<DishesBean, BaseViewHolder> {

    public ApproverAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DishesBean item) {
        helper.setGone(R.id.iv_approver_arrow, helper.getLayoutPosition() != mData.size()-1);
    }
}
