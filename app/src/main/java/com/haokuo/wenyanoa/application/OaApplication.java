package com.haokuo.wenyanoa.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.haokuo.midtitlebar.BarStyle;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.util.DirUtil;
import com.haokuo.wenyanoa.util.utilscode.Utils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zjf on 2018-07-16.
 */

public class OaApplication extends Application {
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary初始化
        //        if (LeakCanary.isInAnalyzerProcess(this)) {
        //            return;
        //        }
        //        LeakCanary.install(this);
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //创建文件夹
        createDirs();
        //初始化工具类
        Utils.init(getApplicationContext());
        //初始化全局Loading样式
        initLoadingStyle();
        initBarStyle();
        //数据库初始化
    }

    private void initBarStyle() {
        Resources resources = getResources();
        BarStyle barStyle = new BarStyle.Builder()
                .setBackgroundColor(resources.getColor(R.color.colorPrimary))
                .setTitleColor(resources.getColor(R.color.colorWhite))
                .setTitleSize(resources.getDimension(R.dimen.sp_19))
                .setHasBackArrow(true)
                .setNavigationIconId(R.drawable.fanhui1)
                .build();
        MidTitleBar.initStyle(barStyle);
    }

    private void initLoadingStyle() {
        StyleManager styleManager = new StyleManager();
        styleManager.loadText("提交中...").successText("提交成功").failedText("提交失败")
                .loadingColor(getResources().getColor(R.color.colorPrimary))
                .speed(LoadingDialog.Speed.SPEED_FAST).showSuccessTime(500).showFailedTime(800).finishSuccess(true);
        LoadingDialog.initStyle(styleManager);
    }

    private void createDirs() {
        DirUtil.createDir();
    }
}
