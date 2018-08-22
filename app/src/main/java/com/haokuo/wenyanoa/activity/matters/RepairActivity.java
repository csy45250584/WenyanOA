package com.haokuo.wenyanoa.activity.matters;

import android.view.View;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.view.ApprovalItem1;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-08-14.
 */

public class RepairActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.item_date)
    ApprovalItem1 mItemDate;
    private Calendar mCurrentDay;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_repair;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mCurrentDay = Calendar.getInstance();
    }

    @Override
    protected void initListener() {
        mItemDate.setOnSelectClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog beginDpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar selectDate = Calendar.getInstance();
                                selectDate.set(year, monthOfYear, dayOfMonth);
                                mItemDate.setSelectText(TimeUtils.mDateFormat.format(selectDate.getTime()));
                            }
                        },
                        mCurrentDay.get(Calendar.YEAR), // Initial year selection
                        mCurrentDay.get(Calendar.MONTH), // Initial month selection
                        mCurrentDay.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                beginDpd.setMinDate(mCurrentDay);
                beginDpd.setTitle("请选择维修时间");
                beginDpd.show(getFragmentManager(), "BeginDatePickerDialog");
            }
        });
    }

    @OnClick({R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                break;
        }
    }
}
