package com.haokuo.wenyanoa.activity;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.view.ApprovalItem1;
import com.rey.material.widget.Button;

import butterknife.BindView;

/**
 * Created by zjf on 2018/9/3.
 */
public class DestinationDetailActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.ai_staff_name)
    ApprovalItem1 mAiStaffName;
    @BindView(R.id.ai_begin_date)
    ApprovalItem1 mAiBeginDate;
    @BindView(R.id.ai_end_date)
    ApprovalItem1 mAiEndDate;
    @BindView(R.id.ai_destination)
    ApprovalItem1 mAiDestination;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_destination_detail;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        int id = getIntent().getIntExtra(StaffDestinationActivity.EXTRA_DESTINATION_ID, -1);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
    }

    @Override
    protected void initListener() {

    }
}
