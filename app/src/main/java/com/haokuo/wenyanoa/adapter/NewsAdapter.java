package com.haokuo.wenyanoa.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.NewsResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class NewsAdapter extends BaseQuickAdapter<NewsResultBean.NewsBean, BaseViewHolder> {

    public NewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsResultBean.NewsBean item) {
        helper.setText(R.id.tv_news_source, item.getCreator());
        helper.setText(R.id.tv_news_post_time, item.getCreateDate());
        helper.setText(R.id.tv_news_content, item.getContent());
    }
}
