package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.ContactDetailActivity;
import com.haokuo.wenyanoa.adapter.ContactsAdapter;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.eventbus.QueryTextChangeEvent;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.ContactsNavigationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-07-09.
 */

public class ContactsFragment extends BaseLazyLoadFragment {

    private static final int MSG_REMOVE_TIPS = 1;
    @BindView(R.id.rv_contacts)
    RecyclerView mRvContacts;
    @BindView(R.id.contacts_navigation)
    ContactsNavigationView mContactsNavigation;
    @BindView(R.id.tv_contact_tips)
    TextView mTvContactTips;
    @BindView(R.id.srl_contacts)
    SmartRefreshLayout mSrlContacts;
    private ContactsAdapter mContactsAdapter;
    private List<ContactResultBean.ContactBean> mContactList;
    private UserIdApiKeyParams mParams;

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_REMOVE_TIPS:
                mTvContactTips.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initListener() {
        mContactsNavigation.setOnWordsChangeListener(new ContactsNavigationView.onWordsChangeListener() {
            @Override
            public void wordsChange(String words) {
                mHandler.removeMessages(MSG_REMOVE_TIPS);
                mTvContactTips.setText(words);
                mTvContactTips.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(MSG_REMOVE_TIPS, 300);
                List<ContactResultBean.ContactBean> data = mContactsAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (words.equalsIgnoreCase(data.get(i).getFirstLetter())) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvContacts.getLayoutManager();
                        if (layoutManager != null) {
                            layoutManager.scrollToPositionWithOffset(i, 0);
                            layoutManager.setStackFromEnd(true);
                        }
                        return;
                    }
                }
            }
        });

        mRvContacts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRvContacts.getLayoutManager();
                if (layoutManager != null) {
                    int position = layoutManager.findFirstVisibleItemPosition();
                    List<ContactResultBean.ContactBean> data = mContactsAdapter.getData();
                    if (data != null && data.size() != 0) {
                        String firstLetter = data.get(position).getFirstLetter();
                        //                    mContactsNavigation.setTouchIndex(firstLetter);
                    }
                }
            }
        });
        mContactsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //                if (mListener != null) {
                //                    mListener.onItemSelect(mContactsAdapter.getItem(position));
                //                } else {
                ContactResultBean.ContactBean item = mContactsAdapter.getItem(position);
                Intent intent = new Intent(mContext, ContactDetailActivity.class);
                intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, item);
                startActivity(intent);
                //                }
            }
        });
        mSrlContacts.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                HttpHelper.getInstance().getContactList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        //更新通讯录
                        ContactResultBean contactResult = JSON.parseObject(json, ContactResultBean.class);
                        mContactList = contactResult.getData();
                        Collections.sort(mContactList);
                        mContactsAdapter.setNewData(mContactList);
                        refreshLayout.finishRefresh();
                        //关闭刷新功能
                        mSrlContacts.setEnableRefresh(false);
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("加载通讯录失败！");
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    @Override
    protected void initData() {
        mRvContacts.setLayoutManager(new LinearLayoutManager(mContext));
        mContactsAdapter = new ContactsAdapter(R.layout.item_contacts);
        mRvContacts.setAdapter(mContactsAdapter);
        mContactList = new ArrayList<>();
        //        for (int i = 0; i < 5; i++) {
        //            mContactList.add(new ContactResultBean.ContactBean("才生", "12349646515"));
        //            mContactList.add(new ContactResultBean.ContactBean("你好", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("黑河哦", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("ff", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("fdfds", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("嘻嘻", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("你放的辅导费", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("发大V", "45642312318"));
        //            mContactList.add(new ContactResultBean.ContactBean("fdfde", "45642312318"));
        //        }
        //        Collections.sort(mContactList);
        //        mContactsAdapter.setNewData(mContactList);
        //关闭加载更多功能
        mSrlContacts.setEnableLoadMore(false);
        UserInfoBean userInfo = OaSpUtil.getUserInfo();
        String apikey = userInfo.getApikey();
        Log.v("MY_CUSTOM_TAG", "ContactsFragment initData()-->" + apikey);
        mParams = new UserIdApiKeyParams(userInfo.getUserId(), apikey);
    }

    @Subscribe
    public void onQueryTextChange(QueryTextChangeEvent event) {
        if (TextUtils.isEmpty(event.getNewText())) {
            mContactsAdapter.setNewData(mContactList);
        }
        String gap = ".*";
        String newText = event.getNewText();
        StringBuilder builder = new StringBuilder(gap);
        for (int i = 0; i < newText.length(); i++) {
            builder.append(newText.charAt(i)).append(gap);
        }
        String regex = builder.toString();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<ContactResultBean.ContactBean> contactBeans = new ArrayList<>();
        for (ContactResultBean.ContactBean contactBean : mContactList) {
            if (pattern.matcher(contactBean.getNamePinyin()).matches() || pattern.matcher(contactBean.getRealname()).matches()) {
                contactBeans.add(contactBean);
            }
        }
        Collections.sort(contactBeans);
        mContactsAdapter.setNewData(contactBeans);
    }

    @Override
    protected void loadData() {
        mSrlContacts.autoRefresh();
    }

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_contacts;
    }

    //    private OnItemSelectListener mListener;
    //
    //    public void setOnItemSelectListener(OnItemSelectListener listener) {
    //        mListener = listener;
    //    }
    //
    //    public interface OnItemSelectListener {
    //        void onItemSelect(ContactResultBean.ContactBean contactBean);
    //    }
}
