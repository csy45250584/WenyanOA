package com.haokuo.wenyanoa.activity.approval;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.ApproverAdapter;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.bean.approval.ApprovalContentBean;
import com.haokuo.wenyanoa.bean.approval.ApprovalUserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.ApprovalDetailParams;
import com.haokuo.wenyanoa.network.bean.HandlerApprovalParams;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.rey.material.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-09.
 */

public class TripApprovalDetailActivity extends BaseApprovalDetailActivity {

    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_approve_state)
    TextView mTvApproveState;
    @BindView(R.id.tv_reject_reason)
    TextView mTvRejectReason;
    @BindView(R.id.ll_reject_container)
    LinearLayout mLlRejectContainer;
    @BindView(R.id.tv_dept_name)
    TextView mTvDeptName;
    @BindView(R.id.tv_duties)
    TextView mTvDuties;
    @BindView(R.id.tv_begin_time)
    TextView mTvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_trip_duration)
    TextView mTvTripDuration;
    @BindView(R.id.tv_trip_reason)
    TextView mTvTripReason;
    @BindView(R.id.rv_approver)
    RecyclerView mRvApprover;
    @BindView(R.id.iv_cc_avatar)
    CircleImageView mIvCcAvatar;
    @BindView(R.id.tv_cc_name)
    TextView mTvCcName;
    @BindView(R.id.btn_reject)
    Button mBtnReject;
    @BindView(R.id.btn_agree)
    Button mBtnAgree;
    @BindView(R.id.ll_btn_container)
    LinearLayout mLlBtnContainer;
    private ApproverAdapter mApproverAdapter;
    private UserInfoBean mUserInfo;
    private int mId;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_trip_approval_detail;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mId = getIntent().getIntExtra(ApprovalActivity.EXTRA_ID, -1);
        int state = getIntent().getIntExtra(ApprovalActivity.EXTRA_STATE, -1);
        mLlBtnContainer.setVisibility(state == 0 ? View.VISIBLE : View.GONE);
        mRvApprover.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mApproverAdapter = new ApproverAdapter(R.layout.item_approver);
        mRvApprover.setAdapter(mApproverAdapter);
        mUserInfo = OaSpUtil.getUserInfo();
        ApprovalDetailParams params = new ApprovalDetailParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mId);

        //请求内容
        showLoading("加载数据中...");
        HttpHelper.getInstance().getTripById(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                loadClose();
                try {
                    String jsonString = new JSONObject(json).getString("trip");
                    ApprovalContentBean resultBean = JSON.parseObject(jsonString, ApprovalContentBean.class);
                    //设置信息
                    applyInfo(resultBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("获取信息失败，" + message);
                loadClose(true);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_reject, R.id.btn_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reject: {
                showRejectDialog();
            }
            break;
            case R.id.btn_agree: {
                HandlerApprovalParams params = new HandlerApprovalParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mId, null);
                showLoading("提交中...");
                HttpHelper.getInstance().agreeTrip(params, mHandleCallback);
            }
            break;
        }
    }

    public void applyInfo(ApprovalContentBean infoBean) {
        ApprovalUserInfoBean userInfo = infoBean.getUserInfo();
        mTvName.setText(userInfo.getRealName());
        mMidTitleBar.setMidTitle(String.format("%s的公差", userInfo.getRealName()));
        ImageLoadUtil.getInstance().loadAvatar(this, userInfo.getHeadPhoto(), mIvAvatar, infoBean.getUser().getSex());
        mTvApproveState.setText(infoBean.getAppState());
        mTvDeptName.setText(userInfo.getSecition());
        mTvDuties.setText(userInfo.getJob());
        mTvBeginTime.setText(infoBean.getStartDate());
        mTvEndTime.setText(infoBean.getEndDate());
        mTvTripDuration.setText(infoBean.getHowlong());
        mTvTripReason.setText(infoBean.getIncident());
        mApproverAdapter.setNewData(infoBean.getApproverList());
        if (infoBean.getCourtesyCopyId() != 0) {
            ImageLoadUtil.getInstance().loadAvatar(this, infoBean.getCopylP(), mIvCcAvatar, infoBean.getCopySex());
            mTvCcName.setText(infoBean.getCopylN());
        }
        if (mTvApproveState.getText() != null && mTvApproveState.getText().toString().contains("拒绝")) {
            mLlRejectContainer.setVisibility(View.VISIBLE);
            mTvRejectReason.setText(infoBean.getRefusefor());
        } else {
            mLlRejectContainer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void rejectApproval(String rejectReason) {
        HandlerApprovalParams params = new HandlerApprovalParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mId, rejectReason);
        showLoading("提交中...");
        HttpHelper.getInstance().rejectTrip(params, mHandleCallback);
    }
}
