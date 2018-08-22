package com.haokuo.wenyanoa.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.MainFragmentPagerAdapter;
import com.haokuo.wenyanoa.adapter.SelectDayAdapter;
import com.haokuo.wenyanoa.fragment.BasketFragment;
import com.haokuo.wenyanoa.fragment.ChooseDishesFragment;
import com.haokuo.wenyanoa.fragment.OrderListFragment;
import com.haokuo.wenyanoa.util.utilscode.SizeUtils;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */

public class OrderFoodActivity extends BaseActivity {
    private static final int DEFAULT_TAB_POSITION = 0;
    private static final String MENU_BASKET = "购买";
    private static final String MENU_ORDER = "金额汇总";

    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.spinner_weekday)
    Spinner mSpinnerWeekday;
    private CustomPopWindow mPopWindow;
    private int mWindowWidth;
    private DatePickerDialog.Builder builder;
    private BasketFragment mBasketFragment;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_order_food;
    }

    @Override
    protected void initData() {
        initToolBar();
        initBottomNaviBar();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChooseDishesFragment());
        mBasketFragment = new BasketFragment();
        fragments.add(mBasketFragment);
        fragments.add(new OrderListFragment());
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size()); //设置缓存fragment数量，防止fragment生命周期多次调用
        mViewPager.setCurrentItem(DEFAULT_TAB_POSITION); //设置初始化的位置
        String[] stringArray = getResources().getStringArray(R.array.weekday);
        List<String> strings = Arrays.asList(stringArray);
        mSpinnerWeekday.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, strings));
        mSpinnerWeekday.setSelection(2);
    }

    private void initToolBar() {
        setSupportActionBar(mMidTitleBar);
        //        mMidTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //            @Override
        //            public boolean onMenuItemClick(MenuItem menuItem) {
        //                switch (menuItem.getTitle().toString()) {
        //                    case MENU_BASKET:
        //                        //提交订单
        //                        //                        new AlertDialog.Builder(OrderFoodActivity.this)
        //                        //                                .setTitle("订单提交")
        //                        //                                .setMessage("金额为")
        //                        break;
        //                    case MENU_ORDER:
        //                        //金额汇总
        //
        //                        break;
        //                }
        //
        //                return true;
        //            }
        //        });
        //        String chineseWeek = TimeUtils.getChineseWeek(TimeUtils.getNowDate());
        //        mMidTitleBar.setMidTitle(chineseWeek);
        mMidTitleBar.addBackArrow(this);
        //        mMidTitleBar.setOnTitleClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                if (mViewPager.getCurrentItem() == 0) {
        //                    if (mPopWindow == null) {
        //                        initPopWindow();
        //                    }
        //                    mPopWindow.showAsDropDown(mMidTitleBar, (ScreenUtils.getScreenWidth() - mWindowWidth) / 2, 0);
        //                }
        //            }
        //        });
    }

    private void initPopWindow() {
        //创建popwindow内容view
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_select_order_day, null);
        //测量view的实际大小
        mWindowWidth = SizeUtils.getMeasuredWidth(popView);
        //创建popwindow
        mPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(popView)//显示的布局，还可以通过设置一个View
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create();//创建PopupWindow
        //处理popView内部逻辑
        RecyclerView rvSelectDay = popView.findViewById(R.id.rv_select_day);
        rvSelectDay.setLayoutManager(new LinearLayoutManager(this));
        rvSelectDay.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, 0x22000000));
        String[] stringArray = getResources().getStringArray(R.array.weekday);
        List<String> weekdays = Arrays.asList(stringArray);
        final SelectDayAdapter selectDayAdapter = new SelectDayAdapter(R.layout.item_select_day, weekdays);
        rvSelectDay.setAdapter(selectDayAdapter);
        selectDayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMidTitleBar.setMidTitle(selectDayAdapter.getItem(position));
                mPopWindow.dissmiss();
                //获取菜单
            }
        });
    }

    private void initBottomNaviBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar //值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
                .setActiveColor(R.color.colorPrimary)//选中颜色 图标和文字
                .setInActiveColor(R.color.text4)//默认未选择颜色
                .setBarBackgroundColor("#FFFFFF");//默认背景色
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.dingcan, "订餐"))
                .addItem(new BottomNavigationItem(R.drawable.gouwuche, "菜篮"))
                .addItem(new BottomNavigationItem(R.drawable.dingdan, "订单"))
                .setFirstSelectedPosition(DEFAULT_TAB_POSITION)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
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
                    case 0:
                        String chineseWeek = TimeUtils.getChineseWeek(TimeUtils.getNowDate());
                        mMidTitleBar.setMidTitle(chineseWeek);
                        mMidTitleBar.getMenu().clear();
                        mMidTitleBar.setMidTitle("");
                        mSpinnerWeekday.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mMidTitleBar.setMidTitle("菜篮");
                        mMidTitleBar.getMenu().clear();
                        mMidTitleBar.getMenu().add(0, 0, 0, MENU_BASKET).setIcon(null).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        mSpinnerWeekday.setVisibility(View.GONE);
                        break;
                    case 2:
                        mMidTitleBar.setMidTitle("订单");
                        mMidTitleBar.getMenu().clear();
                        mMidTitleBar.getMenu().add(0, 0, 0, MENU_ORDER).setIcon(null).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        mSpinnerWeekday.setVisibility(View.GONE);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "购买":
                View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bug_food, null);
                TextView tvTotalPrice = inflate.findViewById(R.id.tv_total_price);
                tvTotalPrice.setText("总价格：30元");
                Dialog.Builder builder = new Dialog.Builder() {
                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.contentView(inflate)
                        .neutralAction("取消")
                        .positiveAction("确定");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
