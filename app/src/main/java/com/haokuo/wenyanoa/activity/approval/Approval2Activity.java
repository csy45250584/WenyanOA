package com.haokuo.wenyanoa.activity.approval;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.adapter.approval.ApprovalApplyItemsAdapter;
import com.haokuo.wenyanoa.adapter.approval.ApprovalBuyItemsAdapter;
import com.haokuo.wenyanoa.adapter.approval.ApprovalChangeShiftAdapter;
import com.haokuo.wenyanoa.adapter.approval.ApprovalLeaveAdapter;
import com.haokuo.wenyanoa.adapter.approval.ApprovalRepairAdapter;
import com.haokuo.wenyanoa.adapter.approval.ApprovalTripAdapter;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.bean.approval.ApproveApplyItemsResultBean;
import com.haokuo.wenyanoa.bean.approval.ApproveBuyItemsResultBean;
import com.haokuo.wenyanoa.bean.approval.ApproveChangeShiftResultBean;
import com.haokuo.wenyanoa.bean.approval.ApproveLeaveResultBean;
import com.haokuo.wenyanoa.bean.approval.ApproveRepairResultBean;
import com.haokuo.wenyanoa.bean.approval.ApproveTripResultBean;
import com.haokuo.wenyanoa.bean.approval.GetIdBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.PageParamWithFillTime;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-09.
 */

public class Approval2Activity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private static final int PAGE_SIZE = 10;
    public static final String EXTRA_ID = "com.haokuo.wenyanoa.extra.EXTRA_ID";
    public static final String EXTRA_STATE = "com.haokuo.wenyanoa.extra.EXTRA_STATE";
    public static final String EXTRA_TYPE = "com.haokuo.wenyanoa.extra.EXTRA_TYPE";
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.indicator_approval_subject)
    CommonTabLayout mIndicatorApprovalSubject;
    @BindView(R.id.rv_approval)
    RecyclerView mRvApproval;
    @BindView(R.id.srl_approval)
    SmartRefreshLayout mSrlApproval;
    private int mCurrentTab;
    private int mCurrentSubTab;
    private PageParamWithFillTime mParams;
    private ApprovalLeaveAdapter mApprovalLeaveAdapter;
    private ApprovalTripAdapter mApprovalTripAdapter;
    private ApprovalChangeShiftAdapter mApprovalChangeShiftAdapter;
    private ApprovalBuyItemsAdapter mApprovalBuyItemsAdapter;
    private ApprovalRepairAdapter mApprovalRepairAdapter;
    private ApprovalApplyItemsAdapter mApprovalApplyItemsAdapter;
    private NetworkCallback mLeaveCallback;
    private NetworkCallback mTripCallback;
    private NetworkCallback mChangeShiftCallback;
    private NetworkCallback mBuyItemsCallback;
    private NetworkCallback mRepairCallback;
    private NetworkCallback mApplyItemsCallback;
    private NetworkCallback mLoadMoreLeaveCallback;
    private NetworkCallback mLoadMoreTripCallback;
    private NetworkCallback mLoadMoreChangeShiftCallback;
    private NetworkCallback mLoadMoreBuyItemsCallback;
    private NetworkCallback mLoadMoreRepairCallback;
    private NetworkCallback mLoadMoreApplyItemsCallback;
    private BottomSheetDialog mBottomSheetDialog;
    private MenuItem mMenuItem;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_approval;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mCurrentTab = getIntent().getIntExtra(EXTRA_TYPE, -1);
        String midTitle = null;
        switch (mCurrentTab) {
            case 0:
                midTitle = "待我审批";
                break;
            case 1:
                midTitle = "我发起的";
                break;
            case 2:
                midTitle = "抄送我的";
                break;
        }
        mMidTitleBar.setMidTitle(midTitle);
        mCurrentSubTab = 0;
        String[] subTitles = {"事假", "公差", "调班", "申购", "报修", "领用"};
        ArrayList<CustomTabEntity> subTitleTabs = new ArrayList<>();
        for (String title : subTitles) {
            subTitleTabs.add(new TitleTab(title));
        }
        mIndicatorApprovalSubject.setTabData(subTitleTabs);
        mRvApproval.setLayoutManager(new LinearLayoutManager(this));
        initAdapters();
        initNetworkCallbacks();
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        String nowString = TimeUtils.getNowString(TIME_FORMAT);
        mParams = new PageParamWithFillTime(userInfo.getUserId(), userInfo.getApikey(), 0, PAGE_SIZE, 0, nowString);
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_approval, menu);
        mMenuItem = menu.getItem(0);
        String nowString = TimeUtils.getNowString(TIME_FORMAT);
        mMenuItem.setTitle(nowString);
        return true;
    }

    private void initAdapters() {
        mApprovalLeaveAdapter = new ApprovalLeaveAdapter(R.layout.item_approve_leave);
        mApprovalTripAdapter = new ApprovalTripAdapter(R.layout.item_approve_trip);
        mApprovalChangeShiftAdapter = new ApprovalChangeShiftAdapter(R.layout.item_approve_change_shift);
        mApprovalBuyItemsAdapter = new ApprovalBuyItemsAdapter(R.layout.item_approve_buy_items);
        mApprovalRepairAdapter = new ApprovalRepairAdapter(R.layout.item_approve_repair);
        mApprovalApplyItemsAdapter = new ApprovalApplyItemsAdapter(R.layout.item_approve_apply_items);
    }

    private void initNetworkCallbacks() {
        mLeaveCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveLeaveResultBean approveLeaveResultBean = JSON.parseObject(json, ApproveLeaveResultBean.class);
                List<ApproveLeaveResultBean.LeaveBean> data = approveLeaveResultBean.getData();
                mApprovalLeaveAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalLeaveAdapter.getData().size() == approveLeaveResultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreLeaveCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveLeaveResultBean approveLeaveResultBean = JSON.parseObject(json, ApproveLeaveResultBean.class);
                List<ApproveLeaveResultBean.LeaveBean> data = approveLeaveResultBean.getData();
                mApprovalLeaveAdapter.addData(data);
                if (mApprovalLeaveAdapter.getData().size() == approveLeaveResultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
        mTripCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveTripResultBean resultBean = JSON.parseObject(json, ApproveTripResultBean.class);
                List<ApproveTripResultBean.TripBean> data = resultBean.getData();
                mApprovalTripAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalTripAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreTripCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveTripResultBean resultBean = JSON.parseObject(json, ApproveTripResultBean.class);
                List<ApproveTripResultBean.TripBean> data = resultBean.getData();
                mApprovalTripAdapter.addData(data);
                if (mApprovalTripAdapter.getData().size() == resultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
        mChangeShiftCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveChangeShiftResultBean resultBean = JSON.parseObject(json, ApproveChangeShiftResultBean.class);
                List<ApproveChangeShiftResultBean.ChangeShiftBean> data = resultBean.getData();
                mApprovalChangeShiftAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalChangeShiftAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreChangeShiftCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveChangeShiftResultBean resultBean = JSON.parseObject(json, ApproveChangeShiftResultBean.class);
                List<ApproveChangeShiftResultBean.ChangeShiftBean> data = resultBean.getData();
                mApprovalChangeShiftAdapter.addData(data);
                if (mApprovalChangeShiftAdapter.getData().size() == resultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
        mBuyItemsCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveBuyItemsResultBean resultBean = JSON.parseObject(json, ApproveBuyItemsResultBean.class);
                List<ApproveBuyItemsResultBean.BuyItemsBean> data = resultBean.getData();
                mApprovalBuyItemsAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalBuyItemsAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreBuyItemsCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveBuyItemsResultBean resultBean = JSON.parseObject(json, ApproveBuyItemsResultBean.class);
                List<ApproveBuyItemsResultBean.BuyItemsBean> data = resultBean.getData();
                mApprovalBuyItemsAdapter.addData(data);
                if (mApprovalBuyItemsAdapter.getData().size() == resultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
        mRepairCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveRepairResultBean resultBean = JSON.parseObject(json, ApproveRepairResultBean.class);
                List<ApproveRepairResultBean.RepairBean> data = resultBean.getData();
                mApprovalRepairAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalRepairAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreRepairCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveRepairResultBean resultBean = JSON.parseObject(json, ApproveRepairResultBean.class);
                List<ApproveRepairResultBean.RepairBean> data = resultBean.getData();
                mApprovalRepairAdapter.addData(data);
                if (mApprovalRepairAdapter.getData().size() == resultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
        mApplyItemsCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveApplyItemsResultBean resultBean = JSON.parseObject(json, ApproveApplyItemsResultBean.class);
                List<ApproveApplyItemsResultBean.ApplyItemsBean> data = resultBean.getData();
                mApprovalApplyItemsAdapter.setNewData(data);
                mSrlApproval.finishRefresh();
                mSrlApproval.setNoMoreData(mApprovalApplyItemsAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishRefresh(false);
            }
        };
        mLoadMoreApplyItemsCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                ApproveApplyItemsResultBean resultBean = JSON.parseObject(json, ApproveApplyItemsResultBean.class);
                List<ApproveApplyItemsResultBean.ApplyItemsBean> data = resultBean.getData();
                mApprovalApplyItemsAdapter.addData(data);
                if (mApprovalApplyItemsAdapter.getData().size() == resultBean.getCount()) {
                    mSrlApproval.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlApproval.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlApproval.finishLoadMore(false);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_time:
                showTimeSelector();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimeSelector() {
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.view_bottom_time_select, null);
        final WheelYearPicker wheelYearPicker = v.findViewById(R.id.wheel_year_picker);
        wheelYearPicker.setYearEnd(Calendar.getInstance().get(Calendar.YEAR));
        final WheelMonthPicker wheelMonthPicker = v.findViewById(R.id.wheel_month_picker);
        Button btnCancel = v.findViewById(R.id.btn_cancel);
        Button btnConfirm = v.findViewById(R.id.btn_confirm);
        String[] split = mParams.getFillformDate().split("-");
        wheelYearPicker.setSelectedYear(Integer.parseInt(split[0]));
        wheelMonthPicker.setSelectedMonth(Integer.parseInt(split[1]));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = wheelYearPicker.getCurrentYear();
                int currentMonth = wheelMonthPicker.getCurrentMonth();
                String time;
                if (currentMonth < 10) {
                    time = currentYear + "-0" + currentMonth;
                } else {
                    time = currentYear + "-" + currentMonth;
                }
                mMenuItem.setTitle(time);
                mParams.setFillformDate(time);
                mSrlApproval.autoRefresh();
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.inDuration(300);
        mBottomSheetDialog.outDuration(300);
        mBottomSheetDialog.contentView(v);
        mBottomSheetDialog.show();
    }

    @Override
    protected void initListener() {
        mIndicatorApprovalSubject.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCurrentSubTab = position;
                if (mCurrentTab != -1) {
                    mSrlApproval.autoRefresh();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mSrlApproval.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshList();
            }
        });

        mApprovalLeaveAdapter.setOnItemClickListener(this);
        mApprovalTripAdapter.setOnItemClickListener(this);
        mApprovalChangeShiftAdapter.setOnItemClickListener(this);
        mApprovalBuyItemsAdapter.setOnItemClickListener(this);
        mApprovalRepairAdapter.setOnItemClickListener(this);
        mApprovalApplyItemsAdapter.setOnItemClickListener(this);
    }

    private void loadMoreList() {
        mParams.increasePageIndex();
        switch (mCurrentTab) {
            case 0: //待我审批
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        HttpHelper.getInstance().getUnapprovedLeave(mParams, mLoadMoreLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        HttpHelper.getInstance().getUnapprovedTrip(mParams, mLoadMoreTripCallback);
                    }
                    break;
                    case 2: {//调班
                        HttpHelper.getInstance().getUnapprovedChangeShift(mParams, mLoadMoreChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        HttpHelper.getInstance().getUnapprovedBuyItems(mParams, mLoadMoreBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        HttpHelper.getInstance().getUnapprovedRepair(mParams, mLoadMoreRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        HttpHelper.getInstance().getUnapprovedApplyItems(mParams, mLoadMoreApplyItemsCallback);
                    }
                    break;
                }
                break;

            case 1: //我发起的
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        HttpHelper.getInstance().getMyLeave(mParams, mLoadMoreLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        HttpHelper.getInstance().getMyTrip(mParams, mLoadMoreTripCallback);
                    }
                    break;
                    case 2: {//调班
                        HttpHelper.getInstance().getMyChangeShift(mParams, mLoadMoreChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        HttpHelper.getInstance().getMyBuyItems(mParams, mLoadMoreBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        HttpHelper.getInstance().getMyRepair(mParams, mLoadMoreRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        HttpHelper.getInstance().getMyApplyItems(mParams, mLoadMoreApplyItemsCallback);
                    }
                    break;
                }
                break;
            case 2://抄送我的
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreTripCallback);
                    }
                    break;
                    case 2: {//调班
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLoadMoreApplyItemsCallback);
                    }
                    break;
                }
                break;
        }
    }

    private void refreshList() {
        mParams.resetPageIndex();
        switch (mCurrentTab) {
            case 0: //待我审批
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        mApprovalLeaveAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalLeaveAdapter);
                        HttpHelper.getInstance().getUnapprovedLeave(mParams, mLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        mApprovalTripAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalTripAdapter);
                        HttpHelper.getInstance().getUnapprovedTrip(mParams, mTripCallback);
                    }
                    break;
                    case 2: {//调班
                        mApprovalChangeShiftAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalChangeShiftAdapter);
                        HttpHelper.getInstance().getUnapprovedChangeShift(mParams, mChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        mApprovalBuyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalBuyItemsAdapter);
                        HttpHelper.getInstance().getUnapprovedBuyItems(mParams, mBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        mApprovalRepairAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalRepairAdapter);
                        HttpHelper.getInstance().getUnapprovedRepair(mParams, mRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        mApprovalApplyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalApplyItemsAdapter);
                        HttpHelper.getInstance().getUnapprovedApplyItems(mParams, mApplyItemsCallback);
                    }
                    break;
                }
                break;

            case 1: //我发起的
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        mApprovalLeaveAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalLeaveAdapter);
                        HttpHelper.getInstance().getMyLeave(mParams, mLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        mApprovalTripAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalTripAdapter);
                        HttpHelper.getInstance().getMyTrip(mParams, mTripCallback);
                    }
                    break;
                    case 2: {//调班
                        mApprovalChangeShiftAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalChangeShiftAdapter);
                        HttpHelper.getInstance().getMyChangeShift(mParams, mChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        mApprovalBuyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalBuyItemsAdapter);
                        HttpHelper.getInstance().getMyBuyItems(mParams, mBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        mApprovalRepairAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalRepairAdapter);
                        HttpHelper.getInstance().getMyRepair(mParams, mRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        mApprovalApplyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalApplyItemsAdapter);
                        HttpHelper.getInstance().getMyApplyItems(mParams, mApplyItemsCallback);
                    }
                    break;
                }
                break;
            case 2://抄送给我
                switch (mCurrentSubTab) {
                    case 0: {//事假
                        mParams.setMatterType(0);
                        mApprovalLeaveAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalLeaveAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mLeaveCallback);
                    }
                    break;
                    case 1: {//公差
                        mParams.setMatterType(1);
                        mApprovalTripAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalTripAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mTripCallback);
                    }
                    break;
                    case 2: {//调班
                        mParams.setMatterType(3);
                        mApprovalChangeShiftAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalChangeShiftAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mChangeShiftCallback);
                    }
                    break;
                    case 3: {//申购
                        mParams.setMatterType(5);
                        mApprovalBuyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalBuyItemsAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mBuyItemsCallback);
                    }
                    break;
                    case 4: {//报修
                        mParams.setMatterType(4);
                        mApprovalRepairAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalRepairAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mRepairCallback);
                    }
                    break;
                    case 5: {//领用
                        mParams.setMatterType(2);
                        mApprovalApplyItemsAdapter.setNewData(null);
                        mRvApproval.setAdapter(mApprovalApplyItemsAdapter);
                        HttpHelper.getInstance().getCopy2MeApproval(mParams, mApplyItemsCallback);
                    }
                    break;
                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Class cls = null;
        if (adapter instanceof ApprovalLeaveAdapter) {
            cls = LeaveApprovalDetailActivity.class;
        } else if (adapter instanceof ApprovalTripAdapter) {
            cls = TripApprovalDetailActivity.class;
        } else if (adapter instanceof ApprovalChangeShiftAdapter) {
            cls = ChangeShiftApprovalDetailActivity.class;
        } else if (adapter instanceof ApprovalBuyItemsAdapter) {
            cls = BuyItemsApprovalDetailActivity.class;
        } else if (adapter instanceof ApprovalRepairAdapter) {
            cls = RepairApprovalDetailActivity.class;
        } else if (adapter instanceof ApprovalApplyItemsAdapter) {
            cls = ApplyItemsApprovalDetailActivity.class;
        }
        GetIdBean item = (GetIdBean) adapter.getItem(position);
        if (item != null) {
            Intent intent = new Intent(Approval2Activity.this, cls);
            intent.putExtra(EXTRA_ID, item.getId());
            intent.putExtra(EXTRA_STATE, mCurrentTab);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshList();
        }
    }

    public static class TitleTab implements CustomTabEntity {

        private String mTitle;

        public TitleTab(String title) {
            mTitle = title;
        }

        @Override
        public String getTabTitle() {
            return mTitle;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
}
