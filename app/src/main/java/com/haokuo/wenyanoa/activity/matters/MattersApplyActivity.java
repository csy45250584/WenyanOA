package com.haokuo.wenyanoa.activity.matters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.adapter.ApprovalApplicationAdapter;
import com.haokuo.wenyanoa.bean.ApplicationBean;
import com.haokuo.wenyanoa.bean.ApplicationSubjectBean;
import com.haokuo.wenyanoa.eventbus.ApprovalAppClickEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-09.
 */

public class MattersApplyActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.rv_approval_application)
    RecyclerView mRvApprovalApplication;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_matters_apply;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mRvApprovalApplication.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ApplicationSubjectBean> applicationSubjectBeans = initAppContent();
        ApprovalApplicationAdapter approvalApplicationAdapter = new ApprovalApplicationAdapter(R.layout.item_approval_application);
        mRvApprovalApplication.setAdapter(approvalApplicationAdapter);
        approvalApplicationAdapter.setNewData(applicationSubjectBeans);
    }

    @NonNull
    private ArrayList<ApplicationSubjectBean> initAppContent() {
        ArrayList<ApplicationSubjectBean> applicationSubjectBeans = new ArrayList<>();
        ArrayList<ApplicationBean> applicationBeans2 = new ArrayList<>();
        applicationBeans2.add(new ApplicationBean(111, "事假申请", R.drawable.shijia));
        applicationBeans2.add(new ApplicationBean(112, "公差申请", R.drawable.gongchai));
        applicationBeans2.add(new ApplicationBean(113, "调班申请", R.drawable.tiaoban2));
        applicationSubjectBeans.add(new ApplicationSubjectBean("调班请假", applicationBeans2));
        ArrayList<ApplicationBean> applicationBeans3 = new ArrayList<>();
        applicationBeans3.add(new ApplicationBean(121, "物品领用", R.drawable.lingyong));
        applicationBeans3.add(new ApplicationBean(122, "报修申请", R.drawable.baoxiu));
        applicationBeans3.add(new ApplicationBean(123, "物品申购", R.drawable.shengou));
        applicationSubjectBeans.add(new ApplicationSubjectBean("其它", applicationBeans3));
        return applicationSubjectBeans;
    }

    @Override
    protected void initListener() {

    }

    @Subscribe
    public void onApprovalAppClick(ApprovalAppClickEvent event) {
        int id = event.getApplicationBean().getId();
        switch (id) {
            case 121:
                startActivity(new Intent(MattersApplyActivity.this, ApplyItemsActivity.class));
                break;
            case 122:
                startActivity(new Intent(MattersApplyActivity.this, RepairActivity.class));
                break;
            case 123:
                startActivity(new Intent(MattersApplyActivity.this, PurchaseItemsActivity.class));
                break;
            case 111:
                startActivity(new Intent(MattersApplyActivity.this, LeaveActivity.class));
                break;
            case 112:
                startActivity(new Intent(MattersApplyActivity.this, TripActivity.class));
                break;
            case 113:
                startActivity(new Intent(MattersApplyActivity.this, ChangeShiftActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
