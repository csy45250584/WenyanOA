package com.haokuo.wenyanoa.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.OrderListAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import lombok.val;

/**
 * Created by zjf on 2018-08-14.
 */

public class ManagerOrderActivity extends BaseActivity {
    @BindView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;

    private Calendar mBeginDay;
    private Calendar mEndDay;
    private Calendar mCurrentDay;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_manager_order;
    }

    @Override
    protected void initData() {
        //设置toolbar
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        //设置recyclerview
        mRvOrderList.setLayoutManager(new LinearLayoutManager(this));
        OrderListAdapter orderListAdapter = new OrderListAdapter(R.layout.item_order_list);
        mRvOrderList.setAdapter(orderListAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        orderListAdapter.setNewData(dishesBeans);
        mCurrentDay = Calendar.getInstance();
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                val beginDpd = DatePickerFragmentDialog.newInstance(
                        new DatePickerFragmentDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
                                if (mBeginDay == null) {
                                    mBeginDay = Calendar.getInstance();
                                }
                                mBeginDay.set(year, monthOfYear, dayOfMonth);
                                mTvStartTime.setText(TimeUtils.CUSTOM_FORMAT.format(mBeginDay.getTime()));
                            }
                        }, mCurrentDay.get(Calendar.YEAR), // Initial year selection
                        mCurrentDay.get(Calendar.MONTH), // Initial month selection
                        mCurrentDay.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                if (mEndDay != null) {
                    beginDpd.setMaxDate(mEndDay);
                }
                beginDpd.setTitle("请选择开始时间");
                beginDpd.show(getSupportFragmentManager(), "BeginDatePickerDialog");
                break;
            case R.id.tv_end_time:
                val endDpd = DatePickerFragmentDialog.newInstance(
                        new DatePickerFragmentDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
                                if (mEndDay == null) {
                                    mEndDay = Calendar.getInstance();
                                }
                                mEndDay.set(year, monthOfYear, dayOfMonth);
                                mTvEndTime.setText(TimeUtils.CUSTOM_FORMAT.format(mEndDay.getTime()));
                            }
                        }, mCurrentDay.get(Calendar.YEAR), // Initial year selection
                        mCurrentDay.get(Calendar.MONTH), // Initial month selection
                        mCurrentDay.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                if (mBeginDay != null) {
                    endDpd.setMinDate(mBeginDay);
                }
                endDpd.setTitle("请选择结束时间");
                endDpd.show(getSupportFragmentManager(), "EndDatePickerDialog");
                break;
        }
    }
}
