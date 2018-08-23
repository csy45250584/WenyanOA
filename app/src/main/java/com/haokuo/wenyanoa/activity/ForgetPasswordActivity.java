package com.haokuo.wenyanoa.activity;

import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.CodeCheckParams;
import com.haokuo.wenyanoa.network.bean.GetResetVerfiyCodeParams;
import com.haokuo.wenyanoa.network.bean.ResetPasswordParams;
import com.haokuo.wenyanoa.util.utilscode.RegexUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
            resetGetVerifyCode();
        }
    };

    private void resetGetVerifyCode() {
        canGetCode = true;
        mTvGetCode.setText("重新获取");
    }


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
            case R.id.tv_get_code: {
                if (canGetCode) {
                    String tel = mEtTel.getEditableText().toString().trim();
                    if (!RegexUtils.isMobileSimple(tel)) {
                        ToastUtils.showShort("请输入正确的手机号码");
                        return;
                    }
                    canGetCode = false;
                    mTvGetCode.setText("发送中");
                    GetResetVerfiyCodeParams params = new GetResetVerfiyCodeParams(tel);
                    HttpHelper.getInstance().getResetVerfiyCode(params, new NetworkCallback() {
                        @Override
                        public void onSuccess(Call call, String json) {
                            countDownTimer.start();
                        }
                        @Override
                        public void onFailure(Call call, String message) {
                            ToastUtils.showShort("获取验证码失败，" + message);
                            resetGetVerifyCode();
                        }
                    });
                }
            }
            break;
            case R.id.btn_next_step: {
                //检查信息
                String tel = mEtTel.getEditableText().toString().trim();
                String code = mEtCode.getEditableText().toString().trim();
                if (!RegexUtils.isMobileSimple(tel)) {
                    ToastUtils.showShort("请输入正确的手机号码");
                    return;
                }
                if (code.length() != CODE_NUM) {
                    ToastUtils.showShort("验证码格式错误");
                    return;
                }
                // 发送请求
                showLoading("验证中...");
                CodeCheckParams params = new CodeCheckParams(tel, code);
                HttpHelper.getInstance().codeCheck(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        loadClose();
                        mLlFirstStep.setVisibility(View.GONE);
                        mLlSecondStep.setVisibility(View.VISIBLE);
                        countDownTimer.cancel();
                        resetGetVerifyCode();
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("验证失败，" + message);
                        countDownTimer.cancel();
                        resetGetVerifyCode();
                        loadClose();
                    }
                });
            }
            break;
            case R.id.btn_reset_password: {
                String tel = mEtTel.getEditableText().toString();
                String code = mEtCode.getEditableText().toString();
                String password = mEtPassword.getEditableText().toString();
                String confirmPassword = mEtConfirmPassword.getEditableText().toString();
                //验证格式
                if (!RegexUtils.isSimplePassword(password)) {
                    ToastUtils.showShort("密码格式错误！");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    ToastUtils.showShort("两次密码不一致，请重新输入");
                    return;
                }
                //发送重置请求
                showLoading("正在提交...");
                ResetPasswordParams params = new ResetPasswordParams(tel, code, password);
                HttpHelper.getInstance().resetPassword(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        loadSuccess("重置密码成功，请登录");
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        loadFailed("重置密码失败，"+message);
                    }
                });
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }
}
