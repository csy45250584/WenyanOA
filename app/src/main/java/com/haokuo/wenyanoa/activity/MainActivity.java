package com.haokuo.wenyanoa.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.BuildConfig;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.MainFragmentPagerAdapter;
import com.haokuo.wenyanoa.bean.UpdateAppResultBean;
import com.haokuo.wenyanoa.eventbus.LogoutEvent;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.fragment.MeFragment;
import com.haokuo.wenyanoa.fragment.WorkFragment;
import com.haokuo.wenyanoa.network.DownloadCallback;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.util.DirUtil;
import com.haokuo.wenyanoa.util.utilscode.IntentUtils;
import com.haokuo.wenyanoa.util.utilscode.KeyboardUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

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
                mSearchView.closeSearch();
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUpdate();
            }
        },1000);
    }

    private void checkUpdate() {
        //检测更新
        HttpHelper.getInstance().getNewVersion(new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                UpdateAppResultBean resultBean = JSON.parseObject(json, UpdateAppResultBean.class);
                if (resultBean.getVersionCode() > BuildConfig.VERSION_CODE) {
                    showUpdateDialog(resultBean);
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("获取更新信息失败," + message);
            }
        });
    }

    private void showUpdateDialog(final UpdateAppResultBean updateAppResultBean) {
        new AlertDialog.Builder(this)
                .setTitle("检测到新版本")
                .setMessage(updateAppResultBean.getUpdateContent())
                .setCancelable(false)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //更新APP
                        String fileName = "WenYanOA_" + updateAppResultBean.getVersionName() + ".apk";
                        downFile(fileName, updateAppResultBean.getUrl());
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    //下载apk操作
    public void downFile(String fileName, final String url) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);//进度条，在下载的时候实时更新进度，提高用户友好度
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍候...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final File apkFile = new File(DirUtil.getUpdateDir(), fileName);
        HttpHelper.getInstance().downloadFile(url, new DownloadCallback(apkFile.getAbsolutePath()) {
            @Override
            public void onStart(Call call, long fileLength) {
                progressDialog.setMax((int) fileLength);
            }

            @Override
            public void onProgress(Call call, long progress) {
                progressDialog.setProgress((int) progress);
            }

            @Override
            public void onSuccess(Call call) {
                progressDialog.dismiss();
                //下载成功，弹出安装应用窗口
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("下载完成");
                builder.setMessage("是否安装？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent installAppIntent = IntentUtils.getInstallAppIntent(apkFile, "com.haokuo.wenyanoa.fileprovider");
                        startActivity(installAppIntent);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }

            @Override
            public void onFailure(Call call, String message) {
                //下载失败
                ToastUtils.showShort("下载APK文件失败，" + message);
                progressDialog.dismiss();
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
                //点击输入法搜索按钮
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //每次输入新的字符串
                mContactsFragment.onQueryTextChange(newText);
                return true;
            }
        });
    }
}
