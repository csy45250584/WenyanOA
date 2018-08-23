package com.haokuo.wenyanoa.activity;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.SuccessBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.UpdatePasswordParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.RegexUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    private UpdatePasswordParams mParams;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        mParams = new UpdatePasswordParams(userInfo.getUserId(), userInfo.getApikey(), null, null);
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String oldPassword = mEtOldPassword.getEditableText().toString();
        String newPassword = mEtNewPassword.getEditableText().toString();
        String confirmPassword = mEtConfirmPassword.getEditableText().toString();
        //验证格式
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            ToastUtils.showShort("请填写完整信息！");
            return;
        }
        if (!RegexUtils.isSimplePassword(oldPassword) || !RegexUtils.isSimplePassword(newPassword)) {
            ToastUtils.showShort("密码格式不对！");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            ToastUtils.showShort("新密码不同！");
            return;
        }
        //发送请求
        mParams.setOldPass(oldPassword);
        mParams.setNewPass(newPassword);
        showLoading("提交中...");
        HttpHelper.getInstance().updatePassword(mParams, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                loadSuccess("修改成功！");
            }

            @Override
            public void onFailure(Call call, String message) {
                loadFailed("修改失败，" + message);
            }
        });
    }
}
