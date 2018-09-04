package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.matters.BaseCcActivity;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.bean.StaffDestinationResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.AddDestinationParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.ApprovalItem1;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    private StaffBean mStaffBean;
    private StaffDestinationResultBean.StaffDestinationBean mBean;
    private UserInfoBean mUserInfo;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_destination_detail;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mBean = (StaffDestinationResultBean.StaffDestinationBean) getIntent().getSerializableExtra(StaffDestinationActivity.EXTRA_DESTINATION);
        if (mBean == null) {
            mMidTitleBar.setMidTitle("新增去向");
            mAiBeginDate.setDateSelector(this, "请选择开始时间");
            mAiEndDate.setDateSelector(this, "请选择结束时间");
            mAiStaffName.setStaffSelector();
        } else {
            mMidTitleBar.setMidTitle("修改去向");
            mAiBeginDate.setDateSelector(this, "请选择开始时间");
            mAiEndDate.setDateSelector(this,"请选择结束时间");
            mAiStaffName.setStaffSelector();
            mAiBeginDate.setSelectText(mBean.getStartDate());
            mAiEndDate.setSelectText(mBean.getEndDate());
            mAiStaffName.setSelectText(mBean.getName());
            mAiDestination.setEditorText(mBean.getLocation());
        }
        mUserInfo = OaSpUtil.getUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseCcActivity.REQUEST_CODE_CC && resultCode == RESULT_OK && data != null) {
            mStaffBean = (StaffBean) data.getSerializableExtra(ContactsFragment.EXTRA_CC);
            if (mStaffBean != null) {
                ApprovalItem1 aiCc = findViewById(R.id.ai_staff_name);
                aiCc.setSelectText(mStaffBean.getName());
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String beginDate = mAiBeginDate.getContentText();
        String endDate = mAiEndDate.getContentText();
        String destination = mAiDestination.getContentText();
        if (TextUtils.isEmpty(mAiStaffName.getContentText()) || TextUtils.isEmpty(beginDate)
                || TextUtils.isEmpty(endDate) || TextUtils.isEmpty(destination)) {
            ToastUtils.showShort("请填写完整信息");
            return;
        }
        showLoading("提交中...");
        if (mBean == null) { //新增
            AddDestinationParams params = new AddDestinationParams(mUserInfo.getUserId(), mUserInfo.getApikey(), -1, mStaffBean.getId(), beginDate, endDate, destination);
            HttpHelper.getInstance().addDestination(params, new NetworkCallback() {
                @Override
                public void onSuccess(Call call, String json) {
                    loadSuccess("提交成功");
                }

                @Override
                public void onFailure(Call call, String message) {
                    loadFailed("提交失败，" + message);
                }
            });
        } else { //修改
            int staffId = mStaffBean == null ? mBean.getUserinfoId() : mStaffBean.getId();
            AddDestinationParams params = new AddDestinationParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mBean.getId(), staffId, beginDate, endDate, destination);
            HttpHelper.getInstance().editDestination(params, new NetworkCallback() {
                @Override
                public void onSuccess(Call call, String json) {
                    loadSuccess("提交成功");
                }

                @Override
                public void onFailure(Call call, String message) {
                    loadFailed("提交失败，" + message);
                }
            });
        }
    }
}
