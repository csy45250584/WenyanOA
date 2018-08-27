package com.haokuo.wenyanoa.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.eventbus.WeekdaySelectedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */

public class ChooseDishesFragment extends BaseLazyLoadFragment {
    //    @BindView(R.id.indicator_dishes)
    //    SmartTabLayout mIndicatorDishes;
    @BindView(R.id.indicator_dishes)
    SlidingTabLayout mIndicatorDishes;
    @BindView(R.id.vp_dishes)
    ViewPager mVpDishes;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_choose_dishes;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        String[] titles = {"早餐", "午餐", "晚餐"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(DishesFragment.newInstance(DishesFragment.TYPE_BREAKFAST));
        fragments.add(DishesFragment.newInstance(DishesFragment.TYPE_LAUNCH));
        fragments.add(DishesFragment.newInstance(DishesFragment.TYPE_DINNER));
        //        mIndicatorDishes.setViewPager(mVpDishes);
        mIndicatorDishes.setViewPager(mVpDishes, titles, mContext, fragments);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(priority = 2)
    public void onWeekdaySelected(WeekdaySelectedEvent event) {
        mIndicatorDishes.setCurrentTab(0, true);
    }
}
