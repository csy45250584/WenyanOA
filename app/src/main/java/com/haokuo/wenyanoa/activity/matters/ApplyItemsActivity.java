package com.haokuo.wenyanoa.activity.matters;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-14.
 */

public class ApplyItemsActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @Override
    protected int initContentLayout() {
        return R.layout.activity_apply_items;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
    }

    @Override
    protected void initListener() {

    }
}
