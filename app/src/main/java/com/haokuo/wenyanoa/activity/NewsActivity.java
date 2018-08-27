package com.haokuo.wenyanoa.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.fragment.NewsFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-10.
 */

public class NewsActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.indicator_news)
    SlidingTabLayout mIndicatorNews;
    @BindView(R.id.vp_news)
    ViewPager mVpNews;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        String[] titles = {"行业新闻", "最新公告", "会议通知"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        NewsFragment conferenceFragment = new NewsFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(NewsFragment.TYPE, NewsFragment.TYPE_NEWS);
        conferenceFragment.setArguments(bundle1);
        fragments.add(conferenceFragment);
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(NewsFragment.TYPE, NewsFragment.TYPE_NOTICE);
        newsFragment.setArguments(bundle2);
        fragments.add(newsFragment);
        NewsFragment noticeFragment = new NewsFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt(NewsFragment.TYPE, NewsFragment.TYPE_CONFERENCE);
        noticeFragment.setArguments(bundle3);
        fragments.add(noticeFragment);
        //        mIndicatorDishes.setViewPager(mVpDishes);
        mIndicatorNews.setViewPager(mVpNews, titles, this, fragments);
    }

    @Override
    protected void initListener() {

    }
}
