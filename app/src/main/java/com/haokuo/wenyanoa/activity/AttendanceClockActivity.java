package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.view.ClockInView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import moe.feng.common.stepperview.VerticalStepperItemView;

/**
 * Created by zjf on 2018-08-09.
 */

public class AttendanceClockActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_view_statistics)
    TextView mTvViewStatistics;
    @BindView(R.id.tv_work_time)
    TextView mTvWorkTime;
    @BindView(R.id.tv_off_work_time)
    TextView mTvOffWorkTime;
    @BindView(R.id.stepper_clock_in)
    VerticalStepperItemView mStepperClockIn;
    @BindView(R.id.stepper_clock_out)
    VerticalStepperItemView mStepperClockOut;
    @BindView(R.id.clock_in_view)
    ClockInView mClockInView;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_attendance_clock;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mStepperClockIn.bindSteppers(null, mStepperClockOut);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_view_statistics, R.id.clock_in_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_view_statistics:
                startActivity(new Intent(this, MyAttendanceActivity.class));
                break;

            case R.id.clock_in_view:
                if (mStepperClockIn.getState() != VerticalStepperItemView.STATE_DONE) {
                    mStepperClockIn.setSummary("fdfdfdfdf");
                    mStepperClockIn.nextStep();
                } else {
                    mStepperClockOut.setState(VerticalStepperItemView.STATE_DONE);
                    mStepperClockOut.setSummary("fdfdfdfdf");
                }
                break;
        }
    }
}
