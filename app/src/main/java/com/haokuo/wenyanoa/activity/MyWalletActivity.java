package com.haokuo.wenyanoa.activity;

import android.view.View;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.view.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-08-08.
 */

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.item_order_food)
    SettingItemView mItemOrderFood;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this, R.drawable.fanhui1);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.item_order_food})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_order_food:

                break;
        }
    }
}
