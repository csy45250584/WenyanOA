package com.haokuo.wenyanoa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.UrlBuilder;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.PhoneUtils;
import com.haokuo.wenyanoa.view.ContactDetailItem;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zjf on 2018-08-07.
 */

public class ContactDetailActivity extends BaseActivity implements ContactDetailItem.OnIconClickListener {
    public static final String EXTRA_CONTACT = "com.haokuo.wenyanoa.extra.EXTRA_CONTACT";
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.contact_phone)
    ContactDetailItem mContactPhone;
    @BindView(R.id.contact_short_phone)
    ContactDetailItem mContactShortPhone;
    @BindView(R.id.contact_office_phone)
    ContactDetailItem mContactOfficePhone;
    @BindView(R.id.contact_duties)
    ContactDetailItem mContactDuties;
    @BindView(R.id.contact_office)
    ContactDetailItem mContactOffice;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private UserInfoBean mUserInfo;
    private ContactResultBean.ContactBean mContactBean;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_contact_detail;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this, R.drawable.fanhui1);
        mContactBean = (ContactResultBean.ContactBean) getIntent().getSerializableExtra(EXTRA_CONTACT);
        if (mContactBean != null) {
            ImageLoadUtil.getInstance().loadAvatar(this, mContactBean.getHeadPhoto(), mIvAvatar, mContactBean.getSex());
            mTvName.setText(mContactBean.getRealname());
            mContactPhone.setContent(mContactBean.getTelphone());
            mContactShortPhone.setContent(mContactBean.getMobilePhone());
            mContactOfficePhone.setContent(mContactBean.getSectionPhone());
            mContactDuties.setContent(mContactBean.getJob());
            mContactOffice.setContent(mContactBean.getSecition());
        }
        mUserInfo = OaSpUtil.getUserInfo();
    }

    @Override
    protected void initListener() {
        mContactPhone.setOnIconClickListener(this);
        mContactShortPhone.setOnIconClickListener(this);
        mContactOfficePhone.setOnIconClickListener(this);
    }

    @Override
    public void onSmsClick(View v, String phone) {
        switch (v.getId()) {
            case R.id.contact_phone:
                String url = UrlBuilder.buildChatOnLineUrl()+"?fromId="+mUserInfo.getUserId()+"&toId="+mContactBean.getId();
                Intent intent = new Intent(ContactDetailActivity.this, ChatOnlineActivity.class);
                intent.putExtra(ChatOnlineActivity.EXTRA_URL,url);
                intent.putExtra(ChatOnlineActivity.EXTRA_NAME,mContactBean.getRealname());
                startActivity(intent);
                //                //发短信
                //                PhoneUtils.sendSms(phone, "");
                //                ChatOnLineParams params = new ChatOnLineParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mUserInfo.getUserId(),  mContactBean.getId());
                //
                //                showLoading("正在获取...");
                //                HttpHelper.getInstance().chatOnLine(params, new NetworkCallback() {
                //                    @Override
                //                    public void onSuccess(Call call, String json) {
                //                        ChatOnlineResultBean resultBean = JSON.parseObject(json, ChatOnlineResultBean.class);
                //                        loadClose();
                //                        Intent intent = new Intent(ContactDetailActivity.this, ChatOnlineActivity.class);
                //                        intent.putExtra(ChatOnlineActivity.EXTRA_URL, resultBean.getChatpath());
                //                        startActivity(intent);
                //                    }
                //
                //                    @Override
                //                    public void onFailure(Call call, String message) {
                //                        loadClose();
                //                        ToastUtils.showShort("获取聊天链接失败，" + message);
                //                    }
                //                });
                break;
        }
    }

    @Override
    public void onPhoneClick(View v, String phone) {
        //打电话
        PhoneUtils.dial(phone);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            dialPhone(phoneNum);
        }
        startActivity(intent);
    }

    public void dialPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
