package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.activity.matters.BaseCcActivity;
import com.haokuo.wenyanoa.activity.matters.SelectCcActivity;
import com.haokuo.wenyanoa.adapter.ApproverAdapter;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.bean.PrepareMatterResultBean;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-14.
 */

public class ApprovalItem2 extends FrameLayout {

    private static final String TAG = "ApprovalItem2";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_approval_item)
    EditText mEtApprovalItem;
    @BindView(R.id.rv_approver)
    RecyclerView mRvApprover;
    @BindView(R.id.iv_cc_avatar)
    ImageView mIvCcAvatar;
    @BindView(R.id.tv_cc_name)
    TextView mTvCcName;
    @BindView(R.id.ll_cc_container)
    LinearLayout mLlCcContainer;
    private ContactResultBean.ContactBean mSelectCc;
    private int mItemType;
    private ApproverAdapter mApproverAdapter;
    private Context mContext;
    private StaffBean mCcBean;

    public ApprovalItem2(@NonNull Context context) {
        this(context, null);
    }

    public ApprovalItem2(@NonNull final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(mContext);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ApprovalItem2);
        String titleText = typedArray.getString(R.styleable.ApprovalItem2_titleText);
        String editorHint = typedArray.getString(R.styleable.ApprovalItem2_editorHint);
        String ccName = typedArray.getString(R.styleable.ApprovalItem2_ccName);
        float editorHeight = typedArray.getDimension(R.styleable.ApprovalItem2_editorHeight, 100);
        mItemType = typedArray.getInt(R.styleable.ApprovalItem2_itemType, 0);
        typedArray.recycle();//释放
        mTvTitle.setText(titleText);
        switch (mItemType) {
            case 0:
                mEtApprovalItem.setHint(editorHint);
                mEtApprovalItem.setHeight((int) editorHeight);
                mLlCcContainer.setVisibility(GONE);
                break;
            case 2:
                mLlCcContainer.setVisibility(GONE);
                mEtApprovalItem.setVisibility(GONE);
                mRvApprover.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                mApproverAdapter = new ApproverAdapter(R.layout.item_approver);
                mRvApprover.setAdapter(mApproverAdapter);
                break;
            case 3:
                mEtApprovalItem.setVisibility(GONE);
                mRvApprover.setVisibility(GONE);
                mTvCcName.setText(ccName);
                mLlCcContainer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseActivity activity = (BaseActivity) context;
                        Intent intent = new Intent(context, SelectCcActivity.class);
                        activity.startActivityForResult(intent, BaseCcActivity.REQUEST_CODE_CC);
                    }
                });
                break;
        }
    }

    private void initView(@NonNull Context context) {
        View inflate = inflate(context, R.layout.view_item_approval2, null);
        ButterKnife.bind(this, inflate);
        addView(inflate);
    }

    public void applyApproverList(PrepareMatterResultBean bean) {
        //检查type是否正确
        if (mItemType != 2) {
            Log.e(TAG, "applyApproverList: " + "type is incorrect");
            return;
        }
        //处理信息
        List<StaffBean> approverList = bean.getApproverList();
        mApproverAdapter.setNewData(approverList);
    }

    public void setCc(StaffBean staffBean) {
        mCcBean = staffBean;
        mTvCcName.setText(staffBean.getName());
        Glide.with(mContext).load(staffBean.getAvatar()).apply(ImageLoadUtil.sAvatarOptions).into(mIvCcAvatar);
    }
    public String getContentText() {
        return mEtApprovalItem.getEditableText().toString();
    }
}
