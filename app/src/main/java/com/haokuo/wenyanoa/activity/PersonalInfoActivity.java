package com.haokuo.wenyanoa.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.view.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.siv_address)
    SettingItemView mSivAddress;

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

    }

    private String sex;

    @OnClick({R.id.tv_change_avatar, R.id.siv_user_name, R.id.siv_real_name, R.id.siv_sex, R.id.siv_tel, R.id.siv_short_tel, R.id.siv_qq, R.id.siv_wechat, R.id.siv_office, R.id.siv_office_phone, R.id.siv_duties, R.id.siv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_avatar:
                break;
            case R.id.siv_user_name:
                View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_change_info_edittext, null);
                new AlertDialog.Builder(this)
                        .setTitle("修改用户名")
                        .setView(inflate)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create().show();

                break;
            case R.id.siv_real_name:
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
                break;
            case R.id.siv_short_tel:
                break;
            case R.id.siv_qq:
                break;
            case R.id.siv_wechat:
                break;
            case R.id.siv_office:
                break;
            case R.id.siv_office_phone:
                break;
            case R.id.siv_duties:
                break;
            case R.id.siv_address:
                break;
        }
    }
}
