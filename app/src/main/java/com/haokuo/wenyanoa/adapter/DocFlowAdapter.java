package com.haokuo.wenyanoa.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.bean.GetDocListResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class DocFlowAdapter extends BaseQuickAdapter<GetDocListResultBean.DocBean, BaseViewHolder> {

    public DocFlowAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GetDocListResultBean.DocBean item) {

    }
}
