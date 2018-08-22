package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;

/**
 * Created by zjf on 2018-07-09.
 */

public class SettingItemView extends FrameLayout {

    private CharSequence mTitle;
    private TextView mTvContent;
    private ImageView mIvRightIcon;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //View
        View inflate = inflate(context, R.layout.view_setting_item, this);

        TextView tvSettingTitle = inflate.findViewById(R.id.tv_setting_title);
        mTvContent = inflate.findViewById(R.id.tv_content);
        ImageView ivSettingIcon = inflate.findViewById(R.id.iv_setting_icon);
        mIvRightIcon = inflate.findViewById(R.id.iv_right_icon);
        //TypedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        mTitle = typedArray.getText(R.styleable.SettingItemView_title);
        float titleSize = typedArray.getDimension(R.styleable.SettingItemView_titleSize, getResources().getDimension(R.dimen.text_30px));
        int titleColor = typedArray.getColor(R.styleable.SettingItemView_titleColor, 0xFF000000);
        int contentColor = typedArray.getColor(R.styleable.SettingItemView_contentColor, 0xFF888888);
        boolean hasRightArrow = typedArray.getBoolean(R.styleable.SettingItemView_hasRightArrow, true);
        CharSequence infoContent = typedArray.getText(R.styleable.SettingItemView_infoContent);
        Drawable icon = typedArray.getDrawable(R.styleable.SettingItemView_icon);
        Drawable rightIcon = typedArray.getDrawable(R.styleable.SettingItemView_rightIcon);
        typedArray.recycle();//释放
        //Set
        tvSettingTitle.setText(mTitle);
        tvSettingTitle.setTextColor(titleColor);
        mTvContent.setTextColor(contentColor);
        tvSettingTitle.setTextSize(titleSize / getResources().getDisplayMetrics().density);
        mTvContent.setText(infoContent);
        if (icon == null) {
            ivSettingIcon.setVisibility(GONE);
        } else {
            ivSettingIcon.setVisibility(VISIBLE);
            ivSettingIcon.setImageDrawable(icon);
        }
        if (rightIcon == null && !hasRightArrow) {
            mIvRightIcon.setVisibility(GONE);
        } else if(hasRightArrow){
            mIvRightIcon.setVisibility(VISIBLE);
        }else {
            mIvRightIcon.setImageDrawable(rightIcon);
            mIvRightIcon.setVisibility(VISIBLE);
        }
    }

    public String getTitle() {
        if (TextUtils.isEmpty(mTitle)) {
            throw new RuntimeException("the title is unassigned !");
        } else {
            return (String) mTitle;
        }
    }

    public void setContentText(String content) {
        mTvContent.setText(content);
    }

    public void setContentColor(int color) {
        mTvContent.setTextColor(color);
    }

    public void setRightIconVisibility(int visibility) {
        mIvRightIcon.setVisibility(visibility);
    }
}
