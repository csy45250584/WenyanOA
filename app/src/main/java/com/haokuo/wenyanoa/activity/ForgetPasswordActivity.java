package com.haokuo.wenyanoa.activity;

import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.util.utilscode.RegexUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-08-13.
 */

public class ForgetPasswordActivity extends BaseActivity {
    private static final int CODE_NUM = 4;
    private static final long TOTAL_TIME = 60 * 1000;
    private static final long ONCE_TIME = 1000;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.et_tel)
    TextInputEditText mEtTel;
    @BindView(R.id.et_code)
    TextInputEditText mEtCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.et_confirm_password)
    TextInputEditText mEtConfirmPassword;
    @BindView(R.id.btn_reset_password)
    Button mBtnResetPassword;
    @BindView(R.id.btn_next_step)
    Button mBtnNextStep;
    @BindView(R.id.ll_first_step)
    LinearLayout mLlFirstStep;
    @BindView(R.id.ll_second_step)
    LinearLayout mLlSecondStep;

    private boolean canGetCode;
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONCE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            mTvGetCode.setText(value + "秒后");
        }

        @Override
        public void onFinish() {
            canGetCode = true;
            mTvGetCode.setText("重新获取");
        }
    };

    @Override
    protected int initContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        canGetCode = true;
        mEtCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(CODE_NUM)});
        unableInputSpace(mEtCode, mEtConfirmPassword, mEtPassword, mEtTel);
        mLlFirstStep.setVisibility(View.VISIBLE);
        mLlSecondStep.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_get_code, R.id.btn_reset_password, R.id.btn_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (canGetCode) {
                    String tel = mEtTel.getEditableText().toString().trim();
                    if (!RegexUtils.isMobileSimple(tel)) {
                        ToastUtils.showShort("请输入正确的手机号码");
                        return;
                    }
                    canGetCode = false;
                    mTvGetCode.setText("发送中");
                    countDownTimer.start();
                }
                break;
            case R.id.btn_next_step:
                mLlFirstStep.setVisibility(View.GONE);
                mLlSecondStep.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_reset_password:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }
}
