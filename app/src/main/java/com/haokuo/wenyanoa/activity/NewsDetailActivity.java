package com.haokuo.wenyanoa.activity;

import android.widget.FrameLayout;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.GetConferenceInfoParams;
import com.haokuo.wenyanoa.network.bean.GetNewsInfoParams;
import com.haokuo.wenyanoa.network.bean.GetNoticeInfoParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018/8/31.
 */
public class NewsDetailActivity extends BaseActivity {
    public static final String EXTRA_NEWS_ID = "com.haokuo.wenyanoa.extra.EXTRA_NEWS_ID";
    public static final String EXTRA_TYPE = "com.haokuo.wenyanoa.extra.EXTRA_TYPE";
    private static final int TYPE_CONFERENCE = 1;
    private static final int TYPE_NEWS = 2;
    private static final int TYPE_NOTICE = 3;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.fl_web_container)
    FrameLayout mFlWebContainer;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initData() {
        final int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, -1);
        int type = getIntent().getIntExtra(EXTRA_TYPE, -1);
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        final AgentWeb.PreAgentWeb ready = AgentWeb.with(this)
                .setAgentWebParent((FrameLayout) mFlWebContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .createAgentWeb()
                .ready();
        NetworkCallback callback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                try {
                    String url = new JSONObject(json).getString("url");
                    ready.go(url+"?id="+newsId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort("加载失败，" + message);
            }
        };
        switch (type) {
            case TYPE_CONFERENCE: {
                mMidTitleBar.setMidTitle("会议通知");
                GetConferenceInfoParams params = new GetConferenceInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getConferenceInfo(params, callback);
            }
            break;
            case TYPE_NEWS: {
                mMidTitleBar.setMidTitle("行业新闻");
                GetNewsInfoParams params = new GetNewsInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getNewsInfo(params, callback);
            }
            break;
            case TYPE_NOTICE: {
                mMidTitleBar.setMidTitle("最新公告");
                GetNoticeInfoParams params = new GetNoticeInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getNoticeInfo(params,callback);
            }
            break;
        }
    }

    @Override
    protected void initListener() {

    }
}
