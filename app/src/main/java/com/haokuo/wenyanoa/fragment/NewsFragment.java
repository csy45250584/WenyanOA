package com.haokuo.wenyanoa.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.NewsAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-10.
 */

public class NewsFragment extends BaseLazyLoadFragment {
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        mRvNews.setLayoutManager(new LinearLayoutManager(mContext));
        NewsAdapter newsAdapter = new NewsAdapter(R.layout.item_news);
        int dividerHeight = (int) (getResources().getDimension(R.dimen.dp_1) + 0.5f);
        mRvNews.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                0, 0));
        mRvNews.setAdapter(newsAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        newsAdapter.setNewData(dishesBeans);
    }

    @Override
    protected void initListener() {

    }
}
