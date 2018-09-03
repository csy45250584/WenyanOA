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
import com.haokuo.wenyanoa.bean.BasketListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.eventbus.WeekdaySelectedEvent;
import com.haokuo.wenyanoa.fragment.BasketFragment;
import com.haokuo.wenyanoa.fragment.ChooseDishesFragment;
import com.haokuo.wenyanoa.fragment.OrderListFragment;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.BuyFoodInBasketParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.SizeUtils;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Spinner;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

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
    private BasketFragment mBasketFragment;
    private ChooseDishesFragment mChooseDishesFragment;
    private UserInfoBean mUserInfo;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_order_food;
    }

    @Override
    protected void initData() {
        initToolBar();
        initBottomNaviBar();
        ArrayList<Fragment> fragments = new ArrayList<>();
        mChooseDishesFragment = new ChooseDishesFragment();
        fragments.add(mChooseDishesFragment);
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
        mUserInfo = OaSpUtil.getUserInfo();
        //        setWeekday(weekIndex);
    }

    private void initToolBar() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
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
        mSpinnerWeekday.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                EventBus.getDefault().post(new WeekdaySelectedEvent(position + 1));
            }
        });
        int weekIndex = TimeUtils.getWeekIndex(TimeUtils.getNowDate());
        weekIndex = weekIndex == 1 ? 7 : weekIndex - 1;
        mSpinnerWeekday.setSelection(weekIndex - 1);
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
                List<BasketListResultBean.BasketBean> basketList = mBasketFragment.getBasketList();
                BigDecimal totalPrice = BigDecimal.valueOf(0);
                StringBuilder stringBuilder = new StringBuilder();
                int totalCount = 0;
                for (BasketListResultBean.BasketBean basketBean : basketList) {
                    if (basketBean.isChecked()) {
                        totalPrice = totalPrice.add(BigDecimal.valueOf(basketBean.getFoodPrice()).multiply(BigDecimal.valueOf(basketBean.getNum())));
                        stringBuilder.append(basketBean.getId()).append(",");
                        totalCount++;
                    }
                }
                if (totalCount == 0) {
                    ToastUtils.showShort("尚未勾选菜品");
                    return super.onOptionsItemSelected(item);
                }
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                String totalPriceString = currencyFormatter.format(totalPrice);
                String ids = stringBuilder.toString();
                ids = ids.substring(0, ids.length() - 1);
                View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bug_food, null);
                TextView tvTotalPrice = inflate.findViewById(R.id.tv_total_price);
                tvTotalPrice.setText(String.format("总价格：%s元", totalPriceString));
                final String finalIds = ids;
                final int finalTotalCount = totalCount;
                Dialog.Builder builder = new Dialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        super.onPositiveActionClicked(fragment);
                        //提交订单
                        showLoading("正在提交订单...");
                        BuyFoodInBasketParams params = new BuyFoodInBasketParams(mUserInfo.getUserId(), mUserInfo.getApikey(), finalTotalCount, finalIds);
                        HttpHelper.getInstance().buyFoodInBasket(params, new NetworkCallback() {
                            @Override
                            public void onSuccess(Call call, String json) {
                                loadSuccess("提交成功", false);
                                mBasketFragment.refreshList();

                            }

                            @Override
                            public void onFailure(Call call, String message) {
                                loadFailed("提交失败," + message);
                            }
                        });
                    }

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

    //    public void setWeekday(int weekday) {
    //
    //        mSpinnerWeekday.setSelection(weekday);
    //        mChooseDishesFragment.onWeekdaySelected(weekday);
    //    }
}
