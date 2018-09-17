package com.haokuo.wenyanoa.activity.matters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.PrepareMatterResultBean;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.LaunchChangeShiftParams;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.ApprovalItem1;
import com.haokuo.wenyanoa.view.ApprovalItem2;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-14.
 */

public class ChangeShiftActivity extends BaseCcActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.ai_transfer_id)
    ApprovalItem1 mAiTransferId;
    @BindView(R.id.ai_original_date)
    ApprovalItem1 mAiOriginalDate;
    @BindView(R.id.ai_change_shift_date)
    ApprovalItem1 mAiChangeShiftDate;
    @BindView(R.id.ai_detail_info)
    ApprovalItem2 mAiDetailInfo;
    @BindView(R.id.ai_approvers)
    ApprovalItem2 mAiApprovers;
    @BindView(R.id.ai_cc)
    ApprovalItem2 mAiCc;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    private UserInfoBean mUserInfo;
    private PrepareMatterResultBean mApproverList;
    private StaffBean mChangeShiftStaff;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_change_shift;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mAiOriginalDate.setDateSelector(this, "请选择原上班日期");
        mAiChangeShiftDate.setDateSelector(this, "请选择调班日期");
        mUserInfo = OaSpUtil.getUserInfo();
        //数据准备
        showLoading("正在准备数据...");
        UserIdApiKeyParams params = new UserIdApiKeyParams(mUserInfo.getUserId(), mUserInfo.getApikey());
        HttpHelper.getInstance().prepareChangeShift(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                mApproverList = JSON.parseObject(json, PrepareMatterResultBean.class);
                loadClose();
                mAiApprovers.applyApproverList(mApproverList);
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("准备数据失败，" + message);
                loadClose(true);
            }
        });
    }

    @Override
    protected void initListener() {
        mAiTransferId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeShiftActivity.this, SelectCcActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHANGE_SHIFT);
            }
        });
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() { //检查数据
        String transferName = mAiTransferId.getContentText();
        String originalDate = mAiOriginalDate.getContentText();
        String changeShiftDate = mAiChangeShiftDate.getContentText();
        String detailInfo = mAiDetailInfo.getContentText();
        if (TextUtils.isEmpty(transferName) || TextUtils.isEmpty(originalDate) || TextUtils.isEmpty(changeShiftDate)) {
            ToastUtils.showShort("请填写完整信息");
            return;
        }
        //发起请求
        showLoading("正在提交请求...");
        int ccId = 0;
        if (mCcBean != null) {
            ccId = mCcBean.getId();
        }
        LaunchChangeShiftParams params = new LaunchChangeShiftParams(mUserInfo.getUserId(), mUserInfo.getApikey(),
                mApproverList.getOneLevelId(), mApproverList.getTwoLevelId(), mApproverList.getThreeLevelId(),
                ccId, originalDate, changeShiftDate, mChangeShiftStaff.getId(), detailInfo,mApproverList.getOneLevelId());
        HttpHelper.getInstance().launchChangeShift(params, new NetworkCallback() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHANGE_SHIFT && resultCode == RESULT_OK && data != null) {
            mChangeShiftStaff = (StaffBean) data.getSerializableExtra(ContactsFragment.EXTRA_CC);
            if (mChangeShiftStaff != null) {
                mAiTransferId.setSelectText(mChangeShiftStaff.getName());
            }
        }
    }
}
