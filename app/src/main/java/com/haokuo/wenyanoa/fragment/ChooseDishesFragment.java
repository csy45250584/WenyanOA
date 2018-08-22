package com.haokuo.wenyanoa.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.haokuo.wenyanoa.R;

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
        String[] titles = {"早餐", "午餐", "晚餐"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DishesFragment());
        fragments.add(new DishesFragment());
        fragments.add(new DishesFragment());
        //        mIndicatorDishes.setViewPager(mVpDishes);
        mIndicatorDishes.setViewPager(mVpDishes, titles, mContext, fragments);
    }

    @Override
    protected void initListener() {

    }
}
