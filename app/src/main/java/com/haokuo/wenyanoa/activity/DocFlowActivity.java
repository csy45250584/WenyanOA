package com.haokuo.wenyanoa.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.DocFlowAdapter;
import com.haokuo.wenyanoa.bean.GetDocListResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.GetDocListParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-09.
 */

public class DocFlowActivity extends BaseActivity {
    private static final int PAGE_SIZE = 10;
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.rv_doc_flow)
    RecyclerView mRvDocFlow;
    @BindView(R.id.srl_doc_flow)
    SmartRefreshLayout mSrlDocFlow;

    private UserInfoBean mUserInfo;
    private DocFlowAdapter mDocFlowAdapter;
    private GetDocListParams mParams;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_doc_flow;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mRvDocFlow.setLayoutManager(new LinearLayoutManager(this));
        mDocFlowAdapter = new DocFlowAdapter(R.layout.item_doc_flow);
        mRvDocFlow.setAdapter(mDocFlowAdapter);
        mUserInfo = OaSpUtil.getUserInfo();
        mParams = new GetDocListParams(mUserInfo.getUserId(), mUserInfo.getApikey(), 0, PAGE_SIZE, 0);
    }

    @Override
    protected void initListener() {

        mSrlDocFlow.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                HttpHelper.getInstance().getDocList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        GetDocListResultBean resultBean = JSON.parseObject(json, GetDocListResultBean.class);
                        List<GetDocListResultBean.DocBean> data = resultBean.getData();
                        mDocFlowAdapter.addData(data);
                        if (mDocFlowAdapter.getData().size() == resultBean.getCount()) {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            refreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取公文列表失败，" + message);
                        refreshLayout.finishLoadMore(false);
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                HttpHelper.getInstance().getDocList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        GetDocListResultBean resultBean = JSON.parseObject(json, GetDocListResultBean.class);
                        List<GetDocListResultBean.DocBean> data = resultBean.getData();
                        mDocFlowAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(mDocFlowAdapter.getData().size() == resultBean.getCount());
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取公文列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
                mDocFlowAdapter .setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });

    }

}
