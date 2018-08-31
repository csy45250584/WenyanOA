package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.NewsDetailActivity;
import com.haokuo.wenyanoa.adapter.NewsAdapter;
import com.haokuo.wenyanoa.bean.NewsResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.PageParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-10.
 */

public class NewsFragment extends BaseLazyLoadFragment {
    public static final String TYPE = "type";
    public static final int TYPE_CONFERENCE = 1;
    public static final int TYPE_NEWS = 2;
    public static final int TYPE_NOTICE = 3;
    private static final int PAGE_SIZE = 10;
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;
    private int type;
    private UserInfoBean mUserInfo;
    private PageParams mParams;
    private NewsAdapter mNewsAdapter;
    private NetworkCallback mRefreshCallback;
    private NetworkCallback mLoadMoreCallback;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
        mRvNews.setLayoutManager(new LinearLayoutManager(mContext));
        mNewsAdapter = new NewsAdapter(R.layout.item_news);
        int dividerHeight = (int) (getResources().getDimension(R.dimen.dp_1) + 0.5f);
        mRvNews.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                0, 0));
        mRvNews.setAdapter(mNewsAdapter);
        mUserInfo = OaSpUtil.getUserInfo();
        mParams = new PageParams(mUserInfo.getUserId(), mUserInfo.getApikey(), 0, PAGE_SIZE);
        initCallback();
    }

    private void initCallback() {
        mRefreshCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                NewsResultBean resultBean = JSON.parseObject(json, NewsResultBean.class);
                List<NewsResultBean.NewsBean> data = resultBean.getData();
                mNewsAdapter.setNewData(data);
                mSrlNews.finishRefresh();
                mSrlNews.setNoMoreData(mNewsAdapter.getData().size() == resultBean.getCount());
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlNews.finishRefresh(false);
            }
        };
        mLoadMoreCallback = new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                NewsResultBean resultBean = JSON.parseObject(json, NewsResultBean.class);
                List<NewsResultBean.NewsBean> data = resultBean.getData();
                mNewsAdapter.addData(data);
                if (mNewsAdapter.getData().size() == resultBean.getCount()) {
                    mSrlNews.finishLoadMoreWithNoMoreData();
                } else {
                    mSrlNews.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call call, String message) {
                ToastUtils.showShort(message);
                mSrlNews.finishLoadMore(false/*,false*/);//传入false表示加载失败
            }
        };
    }

    @Override
    protected void loadData() {
        mSrlNews.autoRefresh();
    }

    @Override
    protected void initListener() {
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsResultBean.NewsBean item = mNewsAdapter.getItem(position);
                if (item != null) {
                    int id = item.getId();
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.EXTRA_TYPE, type);
                    intent.putExtra(NewsDetailActivity.EXTRA_NEWS_ID, id);
                    startActivity(intent);
                }
            }
        });
        mSrlNews.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                switch (type) {
                    case TYPE_CONFERENCE:
                        HttpHelper.getInstance().getConference(mParams, mLoadMoreCallback);
                        break;
                    case TYPE_NEWS:
                        HttpHelper.getInstance().getNewsList(mParams, mLoadMoreCallback);
                        break;
                    case TYPE_NOTICE:
                        HttpHelper.getInstance().getNoticeList(mParams, mLoadMoreCallback);
                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                switch (type) {
                    case TYPE_CONFERENCE: {
                        HttpHelper.getInstance().getConference(mParams, mRefreshCallback);
                    }
                    break;
                    case TYPE_NEWS: {
                        HttpHelper.getInstance().getNewsList(mParams, mRefreshCallback);
                    }
                    break;
                    case TYPE_NOTICE: {
                        HttpHelper.getInstance().getNoticeList(mParams, mRefreshCallback);
                    }
                    break;
                }
            }
        });
    }
}
