package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-06.
 */

public class ItemButton extends FrameLayout {
    @BindView(R.id.tv_item_button)
    TextView mTvItemButton;
    @BindView(R.id.iv_item_button)
    ImageView mIvItemButton;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private Resources mResources;

    public ItemButton(@NonNull Context context) {
        this(context, null);
    }

    public ItemButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        mResources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemButton);
        int recColor = typedArray.getColor(R.styleable.ItemButton_recColor, mResources.getColor(R.color.colorPrimary));
        String iconText = typedArray.getString(R.styleable.ItemButton_iconText);
        String titleText = typedArray.getString(R.styleable.ItemButton_titleText);
        int iconSrc = typedArray.getResourceId(R.styleable.ItemButton_iconSrc, R.mipmap.ic_launcher);
        typedArray.recycle();//释放
        //设置背景样式
        setBgColor(recColor);
        //设置标题内容
        setTitleText(titleText);
        //设置图标或文字内容
        if (!TextUtils.isEmpty(iconText)) {
            setIconText(iconText);
        } else {
            setIconSrc(iconSrc);
        }
    }

    public void setIconSrc(int iconSrc) {
        mIvItemButton.setVisibility(VISIBLE);
        mTvItemButton.setVisibility(GONE);
        mIvItemButton.setImageResource(iconSrc);
    }

    public void setIconText(String iconText) {
        mTvItemButton.setVisibility(VISIBLE);
        mIvItemButton.setVisibility(GONE);
        mTvItemButton.setText(iconText);
    }

    private void initView(@NonNull Context context) {
        View inflate = inflate(context, R.layout.view_item_button, null);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }

    public void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    public void setBgColor(int bgColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(mResources.getDimension(R.dimen.dp_8));
        drawable.setColor(bgColor);
        mFlContainer.setBackground(drawable);
    }
}
