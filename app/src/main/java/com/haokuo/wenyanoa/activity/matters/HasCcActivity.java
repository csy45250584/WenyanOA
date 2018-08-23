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
public abstract class HasCcActivity extends BaseActivity {

    protected StaffBean mCcBean;

    @Override
    protected void initData() {
        mCcBean = new StaffBean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mCcBean = (StaffBean) data.getSerializableExtra(ContactsFragment.EXTRA_CC);
            if (mCcBean != null) {
                ApprovalItem2 aiCc = findViewById(R.id.ai_cc);
                aiCc.setCc(mCcBean);
            }
        }
    }
}
