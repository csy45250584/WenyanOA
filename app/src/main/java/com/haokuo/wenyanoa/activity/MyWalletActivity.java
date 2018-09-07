package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.WalletResultBean;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.SettingItemView;

import java.math.BigDecimal;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-08.
 */

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.item_order_food)
    SettingItemView mItemOrderFood;
    @BindView(R.id.tv_wallet_money)
    TextView mTvWalletMoney;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this, R.drawable.fanhui1);

        mTvWalletMoney.setText("");
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        UserIdApiKeyParams params = new UserIdApiKeyParams(userInfo.getUserId(), userInfo.getApikey());
        HttpHelper.getInstance().getMyWallet(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                WalletResultBean walletResult = JSON.parseObject(json, WalletResultBean.class);
                BigDecimal money = BigDecimal.valueOf(walletResult.getBalance());
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                String moneyString = currencyFormatter.format(money);
                mTvWalletMoney.setText(moneyString);
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("获取钱包信息失败，" + message);
            }
        });
    }



    @Override
    protected void initListener() {

    }

    @OnClick({R.id.item_order_food})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_order_food:
                startActivity(new Intent(this, OrderFoodActivity.class));
                break;
        }
    }
}
