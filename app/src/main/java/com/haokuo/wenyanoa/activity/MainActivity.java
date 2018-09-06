package com.haokuo.wenyanoa.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.MainFragmentPagerAdapter;
import com.haokuo.wenyanoa.eventbus.LogoutEvent;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.fragment.MeFragment;
import com.haokuo.wenyanoa.fragment.WorkFragment;
import com.haokuo.wenyanoa.util.utilscode.KeyboardUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final int DEFAULT_TAB_POSITION = 1;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    private ContactsFragment mContactsFragment;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(this, v);
                return super.dispatchTouchEvent(ev); //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent event) {
        finish();
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        setSupportActionBar(mMidTitleBar);
        initBottomNaviBar();
        ArrayList<Fragment> fragments = new ArrayList<>();
        //        fragments.add(new IMFragment());
        mContactsFragment = new ContactsFragment();
        fragments.add(mContactsFragment);
        fragments.add(new WorkFragment());
        fragments.add(new MeFragment());
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size()); //设置缓存fragment数量，防止fragment生命周期多次调用
        mViewPager.setCurrentItem(DEFAULT_TAB_POSITION); //设置初始化的位置
        mSearchView.setCursorDrawable(R.drawable.search_bar_cursor);
    }

    private void initBottomNaviBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar //值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
                .setActiveColor(R.color.colorPrimary)//选中颜色 图标和文字
                .setInActiveColor(R.color.text4)//默认未选择颜色
                .setBarBackgroundColor("#FFFFFF");//默认背景色
        mBottomNavigationBar
                //                .addItem(new BottomNavigationItem(R.drawable.news, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.tongxun, "通讯录"))
                .addItem(new BottomNavigationItem(R.drawable.work1, "工作"))
                .addItem(new BottomNavigationItem(R.drawable.my, "我的"))
                .setFirstSelectedPosition(DEFAULT_TAB_POSITION)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
                switch (position) {
                    //                    case 0:
                    //                        mMidTitleBar.setMidTitle("消息");
                    //                        mMidTitleBar.getMenu().clear();
                    //                        mSearchView.closeSearch();
                    //                        break;
                    case 1:
                        mMidTitleBar.setMidTitle("闻堰OA");
                        mMidTitleBar.getMenu().clear();
                        mSearchView.closeSearch();
                        break;
                    case 0:
                        mMidTitleBar.setMidTitle("通讯录");
                        mMidTitleBar.getMenu().clear();
                        MenuItem menuItem = mMidTitleBar.getMenu().add(0, R.id.menu_search, 0, null).setIcon(R.drawable.ic_action_action_search);
                        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        mSearchView.setMenuItem(menuItem);
                        break;
                    case 2:
                        mMidTitleBar.setMidTitle("个人中心");
                        mMidTitleBar.getMenu().clear();
                        mSearchView.closeSearch();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mContactsFragment.onQueryTextChange(newText);
                return true;
            }
        });
    }
}
