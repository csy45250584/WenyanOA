package com.haokuo.wenyanoa.activity.matters;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.view.ApprovalItem2;

/**
 * Created by zjf on 2018/8/23.
 */
public abstract class BaseCcActivity extends BaseActivity {
    public static final int REQUEST_CODE_CC = 1;
    public static final int REQUEST_CODE_CHANGE_SHIFT = 2;
    protected StaffBean mCcBean;

    @Override
    protected void initData() {
        mCcBean = new StaffBean();
    }

    public void setCcBean(StaffBean ccBean) {
        mCcBean = ccBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CC && resultCode == RESULT_OK && data != null) {
            mCcBean = (StaffBean) data.getSerializableExtra(ContactsFragment.EXTRA_CC);
            if (mCcBean != null) {
                ApprovalItem2 aiCc = findViewById(R.id.ai_cc);
                aiCc.setCc(mCcBean);
            }
        }
    }
}
