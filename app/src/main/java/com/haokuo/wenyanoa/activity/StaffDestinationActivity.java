package com.haokuo.wenyanoa.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.StaffDestinationAdapter;
import com.haokuo.wenyanoa.bean.StaffDestinationResultBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.consts.SpConsts;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.GetStaffDestinationListParams;
import com.haokuo.wenyanoa.network.bean.base.IdParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.SPUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-09.
 */

public class StaffDestinationActivity extends BaseActivity {
    private static final int PAGE_SIZE = 10;
    public static final String EXTRA_DESTINATION = "com.haokuo.wenyanoa.extra.EXTRA_DESTINATION";
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.rv_staff_destination)
    RecyclerView mRvStaffDestination;
    @BindView(R.id.srl_staff_destination)
    SmartRefreshLayout mSrlStaffDestination;
    @BindView(R.id.et_name)
    EditText mEtName;
    private StaffDestinationAdapter mStaffDestinationAdapter;
    private GetStaffDestinationListParams mParams;
    private UserInfoBean mUserInfo;
    private int mRoleId;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_staff_destination;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mRvStaffDestination.setLayoutManager(new LinearLayoutManager(this));
        int dividerHeight = (int) (getResources().getDimension(R.dimen.dp_1) + 0.5f);
        mRvStaffDestination.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                0, 0));
        mStaffDestinationAdapter = new StaffDestinationAdapter(R.layout.item_staff_destination);
        mRvStaffDestination.setAdapter(mStaffDestinationAdapter);
        mUserInfo = OaSpUtil.getUserInfo();
        mParams = new GetStaffDestinationListParams(mUserInfo.getUserId(), mUserInfo.getApikey(), 0, PAGE_SIZE, "");
        //        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        //        for (int i = 0; i < 20; i++) {
        //            dishesBeans.add(new DishesBean());
        //        }
        //        staffDestinationAdapter.setNewData(dishesBeans);
        SPUtils spUtils = SPUtils.getInstance(SpConsts.FILE_PERSONAL_INFORMATION);
        mRoleId = spUtils.getInt(SpConsts.KEY_ROLE_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSrlStaffDestination.autoRefresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                if (mRoleId == 2 || mRoleId == 12 || mRoleId == 14) {
                    ToastUtils.showShort("账号无权限操作！");
                    break;
                }
                Intent intent = new Intent(StaffDestinationActivity.this, DestinationDetailActivity.class);
                //                intent.putExtra(EXTRA_DESTINATION, mStaffDestinationAdapter.getItem(position).getId());
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_destination_toolbar, menu);
        return true;
    }

    @Override
    protected void initListener() {
        mEtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    mParams.setName(mEtName.getEditableText().toString().trim());
                    mSrlStaffDestination.autoRefresh();
                }
                return false;
            }
        });
        mSrlStaffDestination.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mParams.increasePageIndex();
                HttpHelper.getInstance().getStaffDestinationList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        StaffDestinationResultBean resultBean = JSON.parseObject(json, StaffDestinationResultBean.class);
                        List<StaffDestinationResultBean.StaffDestinationBean> data = resultBean.getData();
                        mStaffDestinationAdapter.addData(data);
                        if (mStaffDestinationAdapter.getData().size() == resultBean.getCount()) {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            refreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取人员去向列表失败，" + message);
                        refreshLayout.finishLoadMore(false);
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                mParams.resetPageIndex();
                HttpHelper.getInstance().getStaffDestinationList(mParams, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        StaffDestinationResultBean resultBean = JSON.parseObject(json, StaffDestinationResultBean.class);
                        List<StaffDestinationResultBean.StaffDestinationBean> data = resultBean.getData();
                        mStaffDestinationAdapter.setNewData(data);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(mStaffDestinationAdapter.getData().size() == resultBean.getCount());
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        ToastUtils.showShort("获取人员去向列表失败，" + message);
                        refreshLayout.finishRefresh(false);
                    }
                });
            }
        });
        //        mStaffDestinationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //
        //            }
        //        });
        mStaffDestinationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.btn_modify:
                        if (mRoleId == 2 || mRoleId == 12 || mRoleId == 14) {
                            ToastUtils.showShort("账号无权限操作！");
                            break;
                        }
                        Intent intent = new Intent(StaffDestinationActivity.this, DestinationDetailActivity.class);
                        intent.putExtra(EXTRA_DESTINATION, mStaffDestinationAdapter.getItem(position));
                        startActivity(intent);
                        break;
                    case R.id.btn_delete:
                        if (mRoleId == 2 || mRoleId == 12 || mRoleId == 14) {
                            ToastUtils.showShort("账号无权限操作！");
                            break;
                        }
                        IdParams params = new IdParams(mUserInfo.getUserId(), mUserInfo.getApikey(), mStaffDestinationAdapter.getItem(position).getId());
                        showLoading("正在删除...");
                        HttpHelper.getInstance().deleteDestination(params, new NetworkCallback() {
                            @Override
                            public void onSuccess(Call call, String json) {
                                mStaffDestinationAdapter.remove(position);
                                loadSuccess("删除成功", false);
                            }

                            @Override
                            public void onFailure(Call call, String message) {
                                loadSuccess("删除失败");
                            }
                        });
                        break;
                }
            }
        });
    }
}
