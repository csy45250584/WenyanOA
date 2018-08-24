package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-07.
 */

public class ContactDetailItem extends FrameLayout {
    @BindView(R.id.tv_contact_title)
    TextView mTvContactTitle;
    @BindView(R.id.tv_contact_content)
    TextView mTvContactContent;
    @BindView(R.id.iv_contact_sms)
    ImageView mIvContactSms;
    @BindView(R.id.iv_contact_phone)
    ImageView mIvContactPhone;
    private Resources mResources;

    public ContactDetailItem(@NonNull Context context) {
        this(context, null);
    }

    public ContactDetailItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        mResources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContactDetailItem);
        String titleText = typedArray.getString(R.styleable.ContactDetailItem_titleText);
        String contentText = typedArray.getString(R.styleable.ContactDetailItem_contentText);
        boolean hasSmsIcon = typedArray.getBoolean(R.styleable.ContactDetailItem_hasSmsIcon, false);
        boolean hasPhoneIcon = typedArray.getBoolean(R.styleable.ContactDetailItem_hasPhoneIcon, false);
        typedArray.recycle();//释放
        mTvContactTitle.setText(titleText);
        mTvContactContent.setText(contentText);
        mIvContactSms.setVisibility(hasSmsIcon ? VISIBLE : GONE);
        mIvContactPhone.setVisibility(hasPhoneIcon ? VISIBLE : GONE);
    }

    private void initView(@NonNull Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_contact_detail_item, this, false);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }

    public void setContent(String text) {
        mTvContactContent.setText(text);
    }

    public String getTvContactContent() {
        return mTvContactContent.getText().toString();
    }

    public void setOnIconClickListener(final OnIconClickListener listener) {
        mIvContactSms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mTvContactContent.getText().toString();
                listener.onSmsClick(ContactDetailItem.this, phone);
            }
        });
        mIvContactPhone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mTvContactContent.getText().toString();
                listener.onPhoneClick(ContactDetailItem.this, phone);
            }
        });
    }

    public interface OnIconClickListener {
        void onSmsClick(View v, String phone);

        void onPhoneClick(View v, String phone);
    }
}
