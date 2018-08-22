package com.haokuo.wenyanoa.activity;

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
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        //        mIndicatorDishes.setViewPager(mVpDishes);
        mIndicatorNews.setViewPager(mVpNews, titles, this, fragments);
    }

    @Override
    protected void initListener() {

    }


}
