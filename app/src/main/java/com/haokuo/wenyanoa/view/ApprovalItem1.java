package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-14.
 */

public class ApprovalItem1 extends FrameLayout {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_approval_item)
    EditText mEtApprovalItem;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.ll_tv_container)
    LinearLayout mLlTvContainer;

    public ApprovalItem1(@NonNull Context context) {
        this(context, null);
    }

    public ApprovalItem1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ApprovalItem1);
        String titleText = typedArray.getString(R.styleable.ApprovalItem1_titleText);
        String editorHint = typedArray.getString(R.styleable.ApprovalItem1_editorHint);
        String selectText = typedArray.getString(R.styleable.ApprovalItem1_selectText);
        int itemType = typedArray.getInt(R.styleable.ApprovalItem1_itemType, 0);
        typedArray.recycle();//释放
        mTvTitle.setText(titleText);
        switch (itemType) {
            case 0:
                mEtApprovalItem.setHint(editorHint);
                mLlTvContainer.setVisibility(GONE);
                break;
            case 1:
                mTvSelect.setText(selectText);
                mEtApprovalItem.setVisibility(GONE);
                break;
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mLlTvContainer.setOnClickListener(l);
        setClickable(true);
    }

    public void setOnSelectClickListener(@Nullable OnClickListener l) {
        mLlTvContainer.setOnClickListener(l);
    }

    private void initView(@NonNull Context context) {
        View inflate = inflate(context, R.layout.view_item_approval1, null);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }

    public void setSelectText(String selectText) {
        mTvSelect.setText(selectText);
    }

    public String getContentText() {
        return mEtApprovalItem.getEditableText().toString();
    }
}
