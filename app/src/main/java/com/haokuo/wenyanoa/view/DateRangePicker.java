package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.haokuo.wenyanoa.R;
import com.rey.material.widget.FrameLayout;

import butterknife.ButterKnife;

/**
 * Created by zjf on 2018/8/22.
 */
public class DateRangePicker extends FrameLayout {
    public DateRangePicker(Context context) {
        this(context,null);
    }

    public DateRangePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemButton);
//        int recColor = typedArray.getColor(R.styleable.ItemButton_recColor, mResources.getColor(R.color.colorPrimary));
        String iconText = typedArray.getString(R.styleable.ItemButton_iconText);
        String titleText = typedArray.getString(R.styleable.ItemButton_titleText);
        int iconSrc = typedArray.getResourceId(R.styleable.ItemButton_iconSrc, R.mipmap.ic_launcher);
        typedArray.recycle();//释放
    }
    private void initView(@NonNull Context context) {
        View inflate = inflate(context, R.layout.view_date_range_picker, null);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }
}
