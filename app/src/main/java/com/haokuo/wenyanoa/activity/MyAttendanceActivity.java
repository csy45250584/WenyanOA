package com.haokuo.wenyanoa.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.daterangepicker.date.DatePickerDialog;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.MyAttendanceAdapter;
import com.haokuo.wenyanoa.bean.MyAttendanceResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.PageWithTimeParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-10.
 */

public class MyAttendanceActivity extends BaseActivity {
    private static final int PAGE_SIZE = 10;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.rv_my_attendance)
    RecyclerView mRvMyAttendance;
    @BindView(R.id.srl_my_attendance)
    SmartRefreshLayout mSrlMyAttendance;
    private PageWithTimeParams mParams;
    private MyAttendanceAdapter mMyAttendanceAdapter;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_my_attendance;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        //设置RecyclerView
        mRvMyAttendance.setLayoutManager(new LinearLayoutManager(this));
        mMyAttendanceAdapter = new MyAttendanceAdapter(R.layout.item_my_attendance, this);
        mRvMyAttendance.setAdapter(mMyAttendanceAdapter);
        //        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        //        for (int i = 0; i < 20; i++) {
        //            dishesBeans.add(new DishesBean());
        //        }
        //        mMyAttendanceAdapter.setNewData(dishesBeans);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        Calendar instance = Calendar.getInstance();
        String endDayStr = TimeUtils.date2String(instance.getTime(), TimeUtils.mDateFormat);
        instance.add(Calendar.MONTH, -1);
        String startDayStr = TimeUtils.date2String(instance.getTime(), TimeUtils.mDateFormat);
        mMidTitleBar.setMidTitle(startDayStr + " ~ " + endDayStr);
        mParams = new PageWithTimeParams(userInfo.getUserId(), userInfo.getApiKey(), 0, PAGE_SIZE, startDayStr, endDayStr);
        mSrlMyAttendance.autoRefresh();
    }

    @Override
    protected void initListener() {
        mSrlMyAttendance.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                HttpHelper.getInstance().getMyAttendanceList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        List<MyAttendanceResultBean.AttendanceBean> data = JSON.parseObject(json, MyAttendanceResultBean.class).getData();
                        if (data != null) {
                            mMyAttendanceAdapter.addData(data);
                            if (data.size() < PAGE_SIZE) {
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                            mParams.increasePageIndex();
                        } else {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort(message);
                        refreshLayout.finishLoadMore(false/*,false*/);//传入false表示加载失败
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                HttpHelper.getInstance().getMyAttendanceList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        List<MyAttendanceResultBean.AttendanceBean> data = JSON.parseObject(json, MyAttendanceResultBean.class).getData();
                        if (data != null) {
                            mMyAttendanceAdapter.setNewData(data);
                            mParams.increasePageIndex();
                        }
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort(message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
        mMidTitleBar.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
                                Calendar instance = Calendar.getInstance();
                                instance.set(year, monthOfYear, dayOfMonth);
                                String startDayStr = TimeUtils.date2String(instance.getTime(), TimeUtils.mDateFormat);
                                instance.set(yearEnd, monthOfYearEnd, dayOfMonthEnd);
                                String endDayStr = TimeUtils.date2String(instance.getTime(), TimeUtils.mDateFormat);
                                mParams.resetPageIndex();
                                mParams.setStartTime(startDayStr);
                                mParams.setEndTime(endDayStr);
                                mSrlMyAttendance.autoRefresh();
                                mMidTitleBar.setMidTitle(startDayStr + " ~ " + endDayStr);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }
}
