package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.LoginActivity;
import com.haokuo.wenyanoa.activity.MyAttendanceActivity;
import com.haokuo.wenyanoa.activity.MyWalletActivity;
import com.haokuo.wenyanoa.activity.PersonalInfoActivity;
import com.haokuo.wenyanoa.activity.SettingActivity;
import com.haokuo.wenyanoa.view.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zjf on 2018-07-09.
 */

public class MeFragment extends BaseLazyLoadFragment {

    @BindView(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_duties)
    TextView mTvUserDuties;
    @BindView(R.id.item_attendance)
    SettingItemView mItemAttendance;
    @BindView(R.id.item_wallet)
    SettingItemView mItemWallet;
    @BindView(R.id.item_personal_info)
    SettingItemView mItemPersonalInfo;
    @BindView(R.id.item_setting)
    SettingItemView mItemSetting;

    @Override
    protected void initListener() {
        mIvUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.item_attendance, R.id.item_wallet, R.id.item_personal_info, R.id.item_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_attendance:
                startActivity(new Intent(mContext, MyAttendanceActivity.class));
                break;
            case R.id.item_wallet:
                startActivity(new Intent(mContext, MyWalletActivity.class));
                break;
            case R.id.item_personal_info:
                startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;
            case R.id.item_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }
}
