package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.LoginResultBean;
import com.haokuo.wenyanoa.consts.SpConsts;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.LoginParams;
import com.haokuo.wenyanoa.util.utilscode.RegexUtils;
import com.haokuo.wenyanoa.util.utilscode.SPUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-10.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    MaterialEditText mEtAccount;
    @BindView(R.id.et_password)
    MaterialEditText mEtPassword;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private SPUtils mInfoSp;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mInfoSp = SPUtils.getInstance(SpConsts.FILE_PERSONAL_INFORMATION);
        unableInputSpace(mEtAccount, mEtPassword);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                // 校验输入格式
                String account = mEtAccount.getEditableText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    ToastUtils.showShort("用户名不能为空！");
                    break;
                }
                String password = mEtPassword.getEditableText().toString().trim();
                if (!RegexUtils.isSimplePassword(password)) {
                    ToastUtils.showShort("密码格式错误，请输入正确密码！");
                    break;
                }
                showLoading("登录中...");
                // 发送请求
                LoginParams params = new LoginParams(account, password);
                HttpHelper.getInstance().login(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        LoginResultBean loginResult = JSON.parseObject(json, LoginResultBean.class);
                        saveInfo2Sp(loginResult);
                        loadSuccess("登录成功");
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        loadFailed(message);
                    }
                });
//                int userId = mInfoSp.getInt(SpConsts.KEY_USER_ID);
//                String apikey = mInfoSp.getString(SpConsts.KEY_API_KEY);
//                GetInFoodListParams params1 = new GetInFoodListParams(userId, apikey, 1, 0, 10);
//                HttpHelper.getInstance().getInFoodList(params1, new NetworkCallback() {
//                    @Override
//                    public void onSuccess(Call call, String json) throws IOException {
//                        Log.v("MY_CUSTOM_TAG", "LoginActivity onSuccess()-->");
//                    }
//
//                    @Override
//                    public void onFailure(Call call, String message) {
//                        Log.v("MY_CUSTOM_TAG", "LoginActivity onFailure()-->" + message);
//                    }
//                });
                break;
        }
    }

    private void saveInfo2Sp(LoginResultBean loginResult) {
        mInfoSp.put(SpConsts.KEY_USERNAME, loginResult.getUserName());
        mInfoSp.put(SpConsts.KEY_USER_ID, loginResult.getUserId());
        mInfoSp.put(SpConsts.KEY_API_KEY, loginResult.getApikey());
    }
}
