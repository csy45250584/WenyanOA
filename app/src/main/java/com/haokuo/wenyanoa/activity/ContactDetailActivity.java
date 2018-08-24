package com.haokuo.wenyanoa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
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

    @Override
    protected int initContentLayout() {
        return R.layout.activity_contact_detail;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this, R.drawable.fanhui1);
        ContactResultBean.ContactBean contactBean = (ContactResultBean.ContactBean) getIntent().getSerializableExtra(EXTRA_CONTACT);
        if (contactBean != null) {
            Glide.with(this).load(contactBean.getHeadPhoto()).apply(ImageLoadUtil.sAvatarOptions).into(mIvAvatar);
            mTvName.setText(contactBean.getRealname());
            mContactPhone.setContent(contactBean.getTelphone());
            mContactShortPhone.setContent(contactBean.getMobilePhone());
            mContactOfficePhone.setContent(contactBean.getSectionPhone());
            mContactDuties.setContent(contactBean.getJob());
            mContactOffice.setContent(contactBean.getSecition());
        }
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
                //发短信
                PhoneUtils.sendSms(phone, "");
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
