package com.haokuo.wenyanoa.activity;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ConferenceDetailBean;
import com.haokuo.wenyanoa.bean.NewsDetailBean;
import com.haokuo.wenyanoa.bean.NoticeDetailBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.GetConferenceInfoParams;
import com.haokuo.wenyanoa.network.bean.GetNewsInfoParams;
import com.haokuo.wenyanoa.network.bean.GetNoticeInfoParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;

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
    @BindView(R.id.tv_news_title)
    TextView mTvNewsTitle;
    @BindView(R.id.tv_news_time)
    TextView mTvNewsTime;
    @BindView(R.id.tv_news_creator)
    TextView mTvNewsCreator;
    @BindView(R.id.tv_news_content)
    TextView mTvNewsContent;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initData() {
        int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, -1);
        int type = getIntent().getIntExtra(EXTRA_TYPE, -1);
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        switch (type) {
            case TYPE_CONFERENCE: {
                mMidTitleBar.setMidTitle("会议通知");
                GetConferenceInfoParams params = new GetConferenceInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getConferenceInfo(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        try {
                            String jsonString = new JSONObject(json).getString("conference");
                            ConferenceDetailBean resultBean = JSON.parseObject(jsonString, ConferenceDetailBean.class);
                            mTvNewsTitle.setText(resultBean.getTheme());
                            mTvNewsTime.setText(resultBean.getCreateDate());
                            mTvNewsCreator.setText(resultBean.getCreator());
                            mTvNewsContent.setText(resultBean.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("加载失败，" + message);
                    }
                });
            }
            break;
            case TYPE_NEWS: {
                mMidTitleBar.setMidTitle("行业新闻");
                GetNewsInfoParams params = new GetNewsInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getNewsInfo(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        try {
                            String jsonString = new JSONObject(json).getString("news");
                            NewsDetailBean resultBean = JSON.parseObject(jsonString, NewsDetailBean.class);
                            mTvNewsTitle.setText(resultBean.getTitle());
                            mTvNewsTime.setText(resultBean.getCreateDate());
                            mTvNewsCreator.setText(resultBean.getCreator());
                            mTvNewsContent.setText(resultBean.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("加载失败，" + message);
                    }
                });
            }
            break;
            case TYPE_NOTICE: {
                mMidTitleBar.setMidTitle("最新公告");
                GetNoticeInfoParams params = new GetNoticeInfoParams(userInfo.getUserId(), userInfo.getApikey(), newsId);
                HttpHelper.getInstance().getNoticeInfo(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        try {
                            String jsonString = new JSONObject(json).getString("newsSort");
                            NoticeDetailBean resultBean = JSON.parseObject(jsonString, NoticeDetailBean.class);
                            mTvNewsTitle.setText(resultBean.getSortName());
                            mTvNewsTime.setText(resultBean.getCreateDate());
                            mTvNewsCreator.setText(resultBean.getCreator());
                            mTvNewsContent.setText(resultBean.getSortContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("加载失败，" + message);
                    }
                });
            }
            break;
        }
    }

    @Override
    protected void initListener() {

    }
}
