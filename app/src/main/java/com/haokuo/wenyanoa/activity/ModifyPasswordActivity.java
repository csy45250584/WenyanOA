package com.haokuo.wenyanoa.activity;

import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-08-10.
 */

public class ModifyPasswordActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.et_old_password)
    TextInputEditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    TextInputEditText mEtNewPassword;
    @BindView(R.id.et_confirm_password)
    TextInputEditText mEtConfirmPassword;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {

    }
}
