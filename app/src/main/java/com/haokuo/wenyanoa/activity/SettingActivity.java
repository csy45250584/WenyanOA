package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.view.View;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.consts.SpConsts;
import com.haokuo.wenyanoa.eventbus.LogoutEvent;
import com.haokuo.wenyanoa.util.utilscode.SPUtils;
import com.haokuo.wenyanoa.view.SettingItemView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-08-10.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.siv_modify_password)
    SettingItemView mSivModifyPassword;
    @BindView(R.id.siv_logout)
    SettingItemView mSivLogout;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.siv_modify_password, R.id.siv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_modify_password:
                startActivity(new Intent(this, ModifyPasswordActivity.class));
                break;
            case R.id.siv_logout:
                SPUtils spUtils = SPUtils.getInstance(SpConsts.FILE_PERSONAL_INFORMATION);
                spUtils.clear();
                startActivity(new Intent(this, LoginActivity.class));
                EventBus.getDefault().post(new LogoutEvent());
                finish();
                break;
        }
    }
}
