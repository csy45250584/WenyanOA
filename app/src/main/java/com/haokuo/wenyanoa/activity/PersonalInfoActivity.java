package com.haokuo.wenyanoa.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.UserInfoDetailBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.view.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-10.
 */

public class PersonalInfoActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_change_avatar)
    TextView mTvChangeAvatar;
    @BindView(R.id.siv_user_name)
    SettingItemView mSivUserName;
    @BindView(R.id.siv_real_name)
    SettingItemView mSivRealName;
    @BindView(R.id.siv_sex)
    SettingItemView mSivSex;
    @BindView(R.id.siv_tel)
    SettingItemView mSivTel;
    @BindView(R.id.siv_short_tel)
    SettingItemView mSivShortTel;
    @BindView(R.id.siv_qq)
    SettingItemView mSivQq;
    @BindView(R.id.siv_wechat)
    SettingItemView mSivWechat;
    @BindView(R.id.siv_office)
    SettingItemView mSivOffice;
    @BindView(R.id.siv_office_phone)
    SettingItemView mSivOfficePhone;
    @BindView(R.id.siv_duties)
    SettingItemView mSivDuties;
    @BindView(R.id.siv_birth_day)
    SettingItemView mSivBirthDay;
    private UserInfoDetailBean mUserInfoDetail;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
    }

    @Override
    protected void initListener() {
        //从SP中加载用户信息
        mUserInfoDetail = OaSpUtil.getUserInfoDetail();
        Glide.with(this).load(mUserInfoDetail.getHeadPhoto())
                .apply(ImageLoadUtil.sAvatarOptions)
                .into(mIvAvatar);
        mSivUserName.setContentText(mUserInfoDetail.getUserName() != null ? mUserInfoDetail.getUserName() : "尚未设置用户名");
        mSivRealName.setContentText(mUserInfoDetail.getRealname() != null ? mUserInfoDetail.getRealname() : "尚未设置真实姓名");
        mSivSex.setContentText(mUserInfoDetail.getSex() != null ? mUserInfoDetail.getSex() : "尚未设置性别");
        mSivBirthDay.setContentText(mUserInfoDetail.getBirthday() != null ? mUserInfoDetail.getBirthday() : "尚未设置出生日期");
        mSivTel.setContentText(mUserInfoDetail.getTelPhone() != null ? mUserInfoDetail.getTelPhone() : "尚未设置手机号码");
        mSivShortTel.setContentText(mUserInfoDetail.getMobilePhone() != null ? mUserInfoDetail.getMobilePhone() : "尚未设置手机短号");
        mSivOfficePhone.setContentText(mUserInfoDetail.getSectionPhone() != null ? mUserInfoDetail.getSectionPhone() : "尚未设置科室电话");
        mSivQq.setContentText(mUserInfoDetail.getQq() != null ? mUserInfoDetail.getQq() : "尚未设置QQ号");
        mSivWechat.setContentText(mUserInfoDetail.getWeChat() != null ? mUserInfoDetail.getWeChat() : "尚未设置微信号");
        mSivOffice.setContentText(mUserInfoDetail.getUserSecition() != null ? mUserInfoDetail.getUserSecition() : "尚未设置所在科室");
        mSivDuties.setContentText(mUserInfoDetail.getUserJob() != null ? mUserInfoDetail.getUserJob() : "尚未设置职务");
    }

    private String sex;

    @OnClick({R.id.tv_change_avatar, R.id.siv_real_name, R.id.siv_sex, R.id.siv_tel, R.id.siv_short_tel, R.id.siv_qq, R.id.siv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_avatar:
                break;
            case R.id.siv_real_name:
                showEditDialog("修改用户名", false, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setRealname(content);
                    }
                });
                break;
            case R.id.siv_sex:
                final String[] sexChoice = {"男", "女"};
                new AlertDialog.Builder(this)
                        .setTitle("修改性别")
                        .setSingleChoiceItems(sexChoice, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sex = sexChoice[which];
                            }
                        })
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create().show();

                break;
            case R.id.siv_tel:
                showEditDialog("修改电话号码", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setTelPhone(content);
                    }
                });
                break;
            case R.id.siv_short_tel:
                showEditDialog("修改手机短号", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setMobilePhone(content);
                    }
                });
                break;
            case R.id.siv_qq:
                showEditDialog("修改QQ号", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setQq(content);
                    }
                });
                break;
            case R.id.siv_wechat:
                showEditDialog("修改微信号", false, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setWeChat(content);
                    }
                });
                break;
        }
    }

    private void showEditDialog(String title, boolean isNumber, final InfoApply infoApply) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_change_info_edittext, null);
        final EditText etPersonalInfo = inflate.findViewById(R.id.et_personal_info);
        etPersonalInfo.setInputType(isNumber ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(inflate)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = etPersonalInfo.getEditableText().toString().trim();
                        infoApply.applyInfo(content);
                        updateUserInfo(mUserInfoDetail);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    private interface InfoApply {
        void applyInfo(String content);
    }

    private void updateUserInfo(final UserInfoDetailBean userInfoDetail) {
        showLoading("提交修改中...");
        HttpHelper.getInstance().updateUserInfo(userInfoDetail, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                loadSuccess("修改成功", false);
                OaSpUtil.saveUserDetailInfo(userInfoDetail);
            }

            @Override
            public void onFailure(Call call, String message) {
                loadFailed("修改失败，" + message);
            }
        });
    }
}
