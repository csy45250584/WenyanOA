package com.haokuo.wenyanoa.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ApplicationSubjectBean;
import com.haokuo.wenyanoa.eventbus.ApprovalAppClickEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ApprovalApplicationAdapter extends BaseQuickAdapter<ApplicationSubjectBean, BaseViewHolder> {

    public ApprovalApplicationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ApplicationSubjectBean item) {
        String title = item.getTitle();
        helper.setText(R.id.tv_app_subject_title, title);
        helper.setGone(R.id.tv_app_subject_title, !TextUtils.isEmpty(title));
        RecyclerView recyclerView = helper.getView(R.id.rv_approval_item);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        ApprovalSubjectAdapter approvalSubjectAdapter = new ApprovalSubjectAdapter(R.layout.item_approval_subject, item.getAppList());
        recyclerView.setAdapter(approvalSubjectAdapter);
        approvalSubjectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new ApprovalAppClickEvent(item.getAppList().get(position)));
            }
        });
    }
}
