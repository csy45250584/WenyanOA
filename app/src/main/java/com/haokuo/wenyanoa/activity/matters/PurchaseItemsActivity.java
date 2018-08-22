package com.haokuo.wenyanoa.activity.matters;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.bean.ApproverListBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.OaSpUtil;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-14.
 */

public class PurchaseItemsActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_purchase_items;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        //数据准备
        UserIdApiKeyParams params = new UserIdApiKeyParams(userInfo.getUserId(), userInfo.getApikey());
        HttpHelper.getInstance().getInBuyItems(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json)  {
                ApproverListBean approverList = JSON.parseObject(json, ApproverListBean.class);
            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
    }

    @Override
    protected void initListener() {

    }
}
