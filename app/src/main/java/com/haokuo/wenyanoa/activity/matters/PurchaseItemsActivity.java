package com.haokuo.wenyanoa.activity.matters;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.PrepareMatterResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.SaveBuyItemsParams;
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

public class PurchaseItemsActivity extends BaseCcActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.ai_goods_name)
    ApprovalItem1 mAiGoodsName;
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

    @Override
    protected int initContentLayout() {
        return R.layout.activity_purchase_items;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mUserInfo = OaSpUtil.getUserInfo();
        //数据准备
        showLoading("正在准备数据...");
        UserIdApiKeyParams params = new UserIdApiKeyParams(mUserInfo.getUserId(), mUserInfo.getApikey());
        HttpHelper.getInstance().getInBuyItems(params, new NetworkCallback() {
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

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        //检查数据
        String goodsName = mAiGoodsName.getContentText();
        String detailInfo = mAiDetailInfo.getContentText();
        if (TextUtils.isEmpty(goodsName)) {
            ToastUtils.showShort("请填写申购物品");
            return;
        }
        //发起请求
        showLoading("正在提交请求...");
        SaveBuyItemsParams params = new SaveBuyItemsParams(mUserInfo.getUserId(), mUserInfo.getApikey(),
                mApproverList.getOneLevelId(), mApproverList.getTwoLevelId(), mApproverList.getThreeLevelId(),
                mCcBean.getId(), goodsName, detailInfo);
        HttpHelper.getInstance().saveBuyItems(params, new NetworkCallback() {
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
