package com.haokuo.wenyanoa.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.OrderListAdapter;
import com.haokuo.wenyanoa.bean.OrderListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.ManageOrderParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lombok.val;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-14.
 */

public class ManagerOrderActivity extends BaseActivity {
    private static final int PAGE_SIZE = 10;
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
    @BindView(R.id.srl_order_list)
    SmartRefreshLayout mSrlOrderList;

    private Calendar mBeginDay;
    private Calendar mEndDay;
    private Calendar mCurrentDay;
    private ManageOrderParams mParams;
    private OrderListAdapter mOrderListAdapter;

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
        mOrderListAdapter = new OrderListAdapter(R.layout.item_order_list);
        mRvOrderList.setAdapter(mOrderListAdapter);
        //        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        //        for (int i = 0; i < 20; i++) {
        //            dishesBeans.add(new DishesBean());
        //        }
        //        orderListAdapter.setNewData(dishesBeans);
        mCurrentDay = Calendar.getInstance();
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        mParams = new ManageOrderParams(userInfo.getUserId(), userInfo.getApikey(), 0, PAGE_SIZE, "", "", "");
        mSrlOrderList.autoRefresh();
    }

    @Override
    protected void initListener() {
        mEtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    String name = mEtName.getEditableText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        mParams.setRealname("");
                    } else {
                        mParams.setRealname(name);
                    }
                    mSrlOrderList.autoRefresh();
                }
                return false;
            }
        });
        mSrlOrderList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                HttpHelper.getInstance().manageOrderList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        OrderListResultBean resultBean = JSON.parseObject(json, OrderListResultBean.class);
                        List<OrderListResultBean.OrderBean> data = resultBean.getData();
                        mOrderListAdapter.addData(data);
                        if (mOrderListAdapter.getData().size() == resultBean.getCount()) {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            refreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取订单列表失败，" + message);
                        refreshLayout.finishLoadMore(false);
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                HttpHelper.getInstance().manageOrderList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        OrderListResultBean resultBean = JSON.parseObject(json, OrderListResultBean.class);
                        List<OrderListResultBean.OrderBean> data = resultBean.getData();
                        mOrderListAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(mOrderListAdapter.getData().size() == resultBean.getCount());
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取订单列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
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
                                String beginTime = TimeUtils.CUSTOM_FORMAT.format(mBeginDay.getTime());
                                mTvStartTime.setText(beginTime);
                                if (mEndDay != null) {
                                    String endTime = mTvEndTime.getText().toString();
                                    String name = mEtName.getEditableText().toString();
                                    mParams.setStartTime(beginTime);
                                    mParams.setEndTime(endTime);
                                    if (TextUtils.isEmpty(name)) {
                                        mParams.setRealname("");
                                    } else {
                                        mParams.setRealname(name);
                                    }
                                    mSrlOrderList.autoRefresh();
                                }
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
                                String endTime = TimeUtils.CUSTOM_FORMAT.format(mEndDay.getTime());
                                mTvEndTime.setText(endTime);
                                if (mBeginDay != null) {
                                    String startTime = mTvStartTime.getText().toString();
                                    mParams.setStartTime(startTime);
                                    mParams.setEndTime(endTime);
                                    String name = mEtName.getEditableText().toString();
                                    if (TextUtils.isEmpty(name)) {
                                        mParams.setRealname("");
                                    } else {
                                        mParams.setRealname(name);
                                    }
                                    mSrlOrderList.autoRefresh();
                                }
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
