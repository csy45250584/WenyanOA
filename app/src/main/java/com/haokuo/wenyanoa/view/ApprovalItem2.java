package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.ApproverAdapter;
import com.haokuo.wenyanoa.adapter.CcAdapter;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-14.
 */

public class ApprovalItem2 extends FrameLayout {

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

    public ApprovalItem2(@NonNull Context context) {
        this(context, null);
    }

    public ApprovalItem2(@NonNull final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ApprovalItem2);
        String titleText = typedArray.getString(R.styleable.ApprovalItem2_titleText);
        String editorHint = typedArray.getString(R.styleable.ApprovalItem2_editorHint);
        String ccName = typedArray.getString(R.styleable.ApprovalItem2_ccName);
        float editorHeight = typedArray.getDimension(R.styleable.ApprovalItem2_editorHeight, 100);
        int itemType = typedArray.getInt(R.styleable.ApprovalItem2_itemType, 0);
        typedArray.recycle();//释放
        mTvTitle.setText(titleText);
        switch (itemType) {
            case 0:
                mEtApprovalItem.setHint(editorHint);
                mEtApprovalItem.setHeight((int) editorHeight);
                mLlCcContainer.setVisibility(GONE);
                break;
            case 2:
                mLlCcContainer.setVisibility(GONE);
                mEtApprovalItem.setVisibility(GONE);
                mRvApprover.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                ApproverAdapter basketAdapter = new ApproverAdapter(R.layout.item_approver);
                mRvApprover.setAdapter(basketAdapter);
                ArrayList<DishesBean> dishesBeans = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    dishesBeans.add(new DishesBean());
                }
                basketAdapter.setNewData(dishesBeans);
                break;
            case 3:
                mEtApprovalItem.setVisibility(GONE);
                mRvApprover.setVisibility(GONE);
                mTvCcName.setText(ccName);
                mLlCcContainer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentActivity fragmentActivity = (FragmentActivity) context;
                        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_select_cc, null);
                        RecyclerView rvSelectCc = inflate.findViewById(R.id.rv_select_cc);
                        rvSelectCc.setLayoutManager(new LinearLayoutManager(context));
                        final CcAdapter ccAdapter = new CcAdapter(R.layout.item_select_cc);
                        rvSelectCc.setAdapter(ccAdapter);
                        final ArrayList<ContactResultBean.ContactBean> contactBeans = new ArrayList<>();
//                        for (int i = 0; i < 20; i++) {
//                            contactBeans.add(new ContactResultBean.ContactBean("ffd", "1232323"));
//                        }
//                        ccAdapter.setNewData(contactBeans);

                        Dialog.Builder builder = new Dialog.Builder();
                        builder.contentView(inflate);
                        //                                .neutralAction("取消")
                        //                                .positiveAction("确定");
                        final DialogFragment fragment = DialogFragment.newInstance(builder);
                        fragment.show(fragmentActivity.getSupportFragmentManager(), null);
                        ccAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                mSelectCc = ccAdapter.getItem(position);
                                mTvCcName.setText(mSelectCc.getRealname());
                                fragment.dismiss();
                            }
                        });
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
}
