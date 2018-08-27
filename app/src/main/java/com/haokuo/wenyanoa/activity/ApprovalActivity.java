package com.haokuo.wenyanoa.activity;

import android.support.v7.widget.RecyclerView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.haokuo.wenyanoa.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-09.
 */

public class ApprovalActivity extends BaseActivity {
    //    @BindView(R.id.mid_title_bar)
    //    MidTitleBar mMidTitleBar;
    @BindView(R.id.indicator_approval)
    CommonTabLayout mIndicatorApproval;
    @BindView(R.id.indicator_approval_subject)
    CommonTabLayout mIndicatorApprovalSubject;
    @BindView(R.id.rv_approval)
    RecyclerView mRvApproval;
    private int mCurrentTab;
    private int mCurrentSubTab;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_approval;
    }

    @Override
    protected void initData() {
        //        setSupportActionBar(mMidTitleBar);
        //        mMidTitleBar.addBackArrow(this);
        mCurrentTab = 0;
        mCurrentSubTab = 0;
        String[] titles = {"待我审批", "我发起的", "抄送我的"};
        String[] subTitles = {"事假", "公差", "调班", "申购", "报修", "领用"};
        ArrayList<CustomTabEntity> titleTabs = new ArrayList<>();
        for (String title : titles) {
            titleTabs.add(new TitleTab(title));
        }
        ArrayList<CustomTabEntity> subTitleTabs = new ArrayList<>();
        for (String title : subTitles) {
            subTitleTabs.add(new TitleTab(title));
        }
        mIndicatorApproval.setTabData(titleTabs);
        mIndicatorApprovalSubject.setTabData(subTitleTabs);
        refreshList();
        initAdapters();
    }

    private void initAdapters() {
//        new
    }

    @Override
    protected void initListener() {
        mIndicatorApproval.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCurrentTab = position;
                if (mCurrentSubTab != -1) {
                    refreshList();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mIndicatorApprovalSubject.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCurrentSubTab = position;
                if (mCurrentTab != -1) {
                    refreshList();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void refreshList() {
        switch (mCurrentTab) {
            case 0:
                switch (mCurrentSubTab) {
                    case 0:

                        break;
                }
                break;
        }
    }

    public static class TitleTab implements CustomTabEntity {

        private String mTitle;

        public TitleTab(String title) {
            mTitle = title;
        }

        @Override
        public String getTabTitle() {
            return mTitle;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
}
