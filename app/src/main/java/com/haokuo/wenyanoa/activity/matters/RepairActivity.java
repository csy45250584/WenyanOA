package com.haokuo.wenyanoa.activity.matters;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.PrepareMatterResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.LaunchRepairParams;
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

public class RepairActivity extends BaseCcActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.ai_date)
    ApprovalItem1 mAiDate;
    @BindView(R.id.ai_goods_name)
    ApprovalItem1 mAiGoodsName;
    @BindView(R.id.ai_address)
    ApprovalItem1 mAiAddress;
    @BindView(R.id.ai_content)
    ApprovalItem2 mAiContent;
    @BindView(R.id.ai_approvers)
    ApprovalItem2 mAiApprovers;
    @BindView(R.id.ai_cc)
    ApprovalItem2 mAiCc;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    private UserInfoBean mUserInfo;
    private PrepareMatterResultBean mApproverList;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_repair;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mAiDate.setDateSelector(this,"请选择维修日期");
        mUserInfo = OaSpUtil.getUserInfo();
        //数据准备
        showLoading("正在准备数据...");
        UserIdApiKeyParams params = new UserIdApiKeyParams(mUserInfo.getUserId(), mUserInfo.getApikey());
        HttpHelper.getInstance().prepareRepair(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                mApproverList = JSON.parseObject(json, PrepareMatterResultBean.class);
                loadClose();
                mAiApprovers.applyApproverList(mApproverList);
                mAiCc.setCc(mApproverList.getCc(), true);
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
    }

    @OnClick({R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                //检查数据
                String goodsName = mAiGoodsName.getContentText();
                String address = mAiAddress.getContentText();
                String date = mAiDate.getContentText();
                String content = mAiContent.getContentText();
                if (TextUtils.isEmpty(goodsName)||TextUtils.isEmpty(address)||TextUtils.isEmpty(date)) {
                    ToastUtils.showShort("请填写完整信息");
                    return;
                }
                //发起请求
                showLoading("正在提交请求...");
                int ccId = 0;
                if (mCcBean != null) {
                    ccId = mCcBean.getId();
                }
                LaunchRepairParams params = new LaunchRepairParams(mUserInfo.getUserId(), mUserInfo.getApikey(),
                        mApproverList.getOneLevelId(), mApproverList.getTwoLevelId(), mApproverList.getThreeLevelId(),
                        ccId, date,goodsName,content,address,null);
                HttpHelper.getInstance().launchRepair(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        loadSuccess("提交成功");
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        loadFailed("提交失败，" + message);
                    }
                });
                break;
        }
    }


}
