package com.haokuo.wenyanoa.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.LogUtils;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;

import butterknife.BindView;

/**
 * Created by zjf on 2018/9/12.
 */
public class ChatOnlineActivity extends BaseActivity {
    public static final String EXTRA_URL = "com.haokuo.wenyanoa.extra.EXTRA_URL";
    public static final String EXTRA_NAME = "com.haokuo.wenyanoa.extra.EXTRA_NAME";
    private static final String TAG = "ChatOnlineActivity";
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.fl_web_container)
    FrameLayout mFlWebContainer;
    private AgentWeb mAgentWeb;
    protected DownloadListenerAdapter mDownloadListenerAdapter;
    private DownloadingService mDownloadingService;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_chat_on_line;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        String url = getIntent().getStringExtra(EXTRA_URL);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        mMidTitleBar.setMidTitle("与 " + name + " 的聊天");
        /**
         * 更新于 AgentWeb  4.0.0
         */
        mDownloadListenerAdapter = new DownloadListenerAdapter() {

            /**
             *
             * @param url                下载链接
             * @param userAgent          UserAgent
             * @param contentDisposition ContentDisposition
             * @param mimetype           资源的媒体类型
             * @param contentLength      文件长度
             * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
             * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
             */
            @Override
            public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, AgentWebDownloader.Extra extra) {
                LogUtils.i(TAG, "onStart:" + url);
                extra.setOpenBreakPointDownload(true) // 是否开启断点续传
                        .setIcon(R.drawable.ic_file_download_black_24dp) //下载通知的icon
                        .setConnectTimeOut(6000) // 连接最大时长
                        .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                        .setDownloadTimeOut(Long.MAX_VALUE) // 下载最大时长
                        .setParallelDownload(false)  // 串行下载更节省资源哦
                        .setEnableIndicator(true)  // false 关闭进度通知
                        .addHeader("Cookie", "xx") // 自定义请求头
                        .setAutoOpen(true) // 下载完成自动打开
                        .setForceDownload(true); // 强制下载，不管网络网络类型
                return false;
            }

            /**
             *
             * 不需要暂停或者停止下载该方法可以不必实现
             * @param url
             * @param downloadingService  用户可以通过 DownloadingService#shutdownNow 终止下载
             */
            @Override
            public void onBindService(String url, DownloadingService downloadingService) {
                super.onBindService(url, downloadingService);
                mDownloadingService = downloadingService;
                LogUtils.i(TAG, "onBindService:" + url + "  DownloadingService:" + downloadingService);
            }

            /**
             * 回调onUnbindService方法，让用户释放掉 DownloadingService。
             * @param url
             * @param downloadingService
             */
            @Override
            public void onUnbindService(String url, DownloadingService downloadingService) {
                super.onUnbindService(url, downloadingService);
                mDownloadingService = null;
                LogUtils.i(TAG, "onUnbindService:" + url);
            }

            /**
             *
             * @param url  下载链接
             * @param loaded  已经下载的长度
             * @param length    文件的总大小
             * @param usedTime   耗时 ，单位ms
             * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
             */
            @Override
            public void onProgress(String url, long loaded, long length, long usedTime) {
                int mProgress = (int) ((loaded) / Float.valueOf(length) * 100);
                LogUtils.i(TAG, "onProgress:" + mProgress);
                super.onProgress(url, loaded, length, usedTime);
            }

            /**
             *
             * @param path 文件的绝对路径
             * @param url  下载地址
             * @param throwable    如果异常，返回给用户异常
             * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
             */
            @Override
            public boolean onResult(String path, String url, Throwable throwable) {
                if (null == throwable) { //下载成功
                    //do you work
                } else {//下载失败

                }
                return false; // true  不会发出下载完成的通知 , 或者打开文件
            }
        };

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((FrameLayout) mFlWebContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    protected void initListener() {

    }

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i(TAG, "mUrl:" + url + "  permission:" + JSON.toJSONString(permissions) + " action:" + action);
            return false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户点击了同意授权
                    //                    call();
                } else {
                    //用户拒绝了授权
                    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void requestPower(String permissions) {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                permissions)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permissions)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{permissions}, 1);
            }
        }
    }

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }
        };
    }
}
