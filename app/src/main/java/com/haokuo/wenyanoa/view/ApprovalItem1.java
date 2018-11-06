package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.activity.matters.BaseCcActivity;
import com.haokuo.wenyanoa.activity.matters.SelectCcActivity;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-14.
 */

public class ApprovalItem1 extends FrameLayout {
    private Calendar mCurrentDay;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_approval_item)
    EditText mEtApprovalItem;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.ll_tv_container)
    LinearLayout mLlTvContainer;
    private int mItemType;
    private String mSelectText;
    private Context mContext;

    public ApprovalItem1(@NonNull Context context) {
        this(context, null);
    }

    public ApprovalItem1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        mContext = context;
        mCurrentDay = Calendar.getInstance();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ApprovalItem1);
        String titleText = typedArray.getString(R.styleable.ApprovalItem1_titleText);
        String editorHint = typedArray.getString(R.styleable.ApprovalItem1_editorHint);
        mSelectText = typedArray.getString(R.styleable.ApprovalItem1_selectText);
        mItemType = typedArray.getInt(R.styleable.ApprovalItem1_itemType, 0);
        typedArray.recycle();//释放
        mTvTitle.setText(titleText);
        switch (mItemType) {
            case 0:
                mEtApprovalItem.setHint(editorHint);
                mLlTvContainer.setVisibility(GONE);
                break;
            case 1:
                mTvSelect.setText(mSelectText);
                mEtApprovalItem.setVisibility(GONE);
                break;
            //            case 2:
            //                mTvSelect.setText(selectText);
            //                mEtApprovalItem.setVisibility(GONE);
            //                break;
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mLlTvContainer.setOnClickListener(l);
    }

    public void setDateSelector(final FragmentActivity activity, final String title) {
        mLlTvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog beginDpd = DatePickerFragmentDialog.newInstance(
                        new DatePickerFragmentDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar selectDate = Calendar.getInstance();
                                selectDate.set(year, monthOfYear, dayOfMonth);
                                mTvSelect.setText(TimeUtils.CUSTOM_FORMAT.format(selectDate.getTime()));
                            }
                        },
                        mCurrentDay.get(Calendar.YEAR), // Initial year selection
                        mCurrentDay.get(Calendar.MONTH), // Initial month selection
                        mCurrentDay.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                beginDpd.setMinDate(mCurrentDay);
                beginDpd.setTitle(title);
                beginDpd.show(activity.getSupportFragmentManager(), "BeginDatePickerDialog");
            }
        });
    }

    public void setDateAndTimeSelector(final FragmentActivity activity, final String title) {
        mLlTvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.Builder builder = new DatePickerDialog.Builder(R.style.MyDatePicker) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        Calendar calendar = dialog.getCalendar();
                        showTimeSelect(calendar);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.title(title)
                        .positiveAction("确定")
                        .negativeAction("取消");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(((BaseActivity) mContext).getSupportFragmentManager(), null);
            }
        });
    }

    private void showTimeSelect(final Calendar selectDate) {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(R.style.Material_App_Dialog_TimePicker_Light, 24, 00) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                selectDate.set(Calendar.HOUR_OF_DAY, dialog.getHour());
                selectDate.set(Calendar.MINUTE, dialog.getMinute());
                mTvSelect.setText(TimeUtils.DEFAULT_FORMAT.format(selectDate.getTime()));
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder
                .positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) mContext).getSupportFragmentManager(), null);
    }

    public void setStaffSelector() {
        mLlTvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity activity = (BaseActivity) mContext;
                Intent intent = new Intent(mContext, SelectCcActivity.class);
                activity.startActivityForResult(intent, BaseCcActivity.REQUEST_CODE_CC);
            }
        });
    }

    public void setSingleChoiceSelector(final String title, final String[] choiceItems) {
        mLlTvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle(title)
                        .setSingleChoiceItems(choiceItems, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTvSelect.setText(choiceItems[which]);
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
    }

    private void initView(@NonNull Context context) {
        View inflate = inflate(context, R.layout.view_item_approval1, null);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }

    public void setSelectText(String selectText) {
        mTvSelect.setText(selectText);
    }

    public void setEditorText(String editorText) {
        mEtApprovalItem.setText(editorText);
    }

    public String getContentText() {
        if (mItemType == 0) {
            return mEtApprovalItem.getEditableText().toString();
        } else {
            if (mSelectText.equals(mTvSelect.getText().toString())) {
                return null;
            }
            return mTvSelect.getText().toString();
        }
    }
}
