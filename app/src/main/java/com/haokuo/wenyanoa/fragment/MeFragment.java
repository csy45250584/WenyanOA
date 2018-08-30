package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.LoginActivity;
import com.haokuo.wenyanoa.activity.MyAttendanceActivity;
import com.haokuo.wenyanoa.activity.MyWalletActivity;
import com.haokuo.wenyanoa.activity.PersonalInfoActivity;
import com.haokuo.wenyanoa.activity.SettingActivity;
import com.haokuo.wenyanoa.bean.UserInfoDetailBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

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
    public void onResume() {
        super.onResume();
        //在SP文件中加载用户数据
        UserInfoDetailBean userInfoDetail = OaSpUtil.getUserInfoDetail();
        setUserUiFromSp(userInfoDetail);
    }

    @Override
    protected void initData() {
        UserInfoDetailBean userInfoDetail = OaSpUtil.getUserInfoDetail();
        //从网络请求更新
        UserIdApiKeyParams params = new UserIdApiKeyParams(userInfoDetail.getUserId(), userInfoDetail.getApikey());
        HttpHelper.getInstance().getUserInfoUrl(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                UserInfoDetailBean userInfoDetailResult = JSON.parseObject(json, UserInfoDetailBean.class);
                OaSpUtil.saveUserDetailInfo(userInfoDetailResult);
                setUserUiFromSp(userInfoDetailResult);
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("加载用户信息失败，" + message);
            }
        });
    }

    private void setUserUiFromSp(UserInfoDetailBean userInfoDetail) {
        String realName = userInfoDetail.getRealname();
        mTvUserName.setText(realName);
        String userJob = userInfoDetail.getUserJob();
        mTvUserDuties.setText(userJob != null ? String.format("职务：%s", userJob) : "");
        String avatarUrl = userInfoDetail.getHeadPhoto();
        ImageLoadUtil.getInstance().loadAvatar(getActivity(),avatarUrl,mIvUserAvatar,userInfoDetail.getSex());
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
