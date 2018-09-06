package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.AttendanceClockActivity;
import com.haokuo.wenyanoa.activity.ManagerOrderActivity;
import com.haokuo.wenyanoa.activity.MyAttendanceActivity;
import com.haokuo.wenyanoa.activity.NewsActivity;
import com.haokuo.wenyanoa.activity.OrderFoodActivity;
import com.haokuo.wenyanoa.activity.StaffDestinationActivity;
import com.haokuo.wenyanoa.activity.approval.ApprovalActivity;
import com.haokuo.wenyanoa.activity.matters.LeaveActivity;
import com.haokuo.wenyanoa.activity.matters.MattersApplyActivity;
import com.haokuo.wenyanoa.adapter.MainApplicationAdapter;
import com.haokuo.wenyanoa.bean.ApplicationBean;
import com.haokuo.wenyanoa.bean.GetAppSomeCountBean;
import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.consts.SpConsts;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.SPUtils;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.ItemButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by zjf on 2018-07-09.
 */

public class WorkFragment extends BaseLazyLoadFragment {

    @BindView(R.id.iv_banner)
    ImageView mIvBanner;
    @BindView(R.id.btn_approval)
    ItemButton mBtnApproval;
    @BindView(R.id.btn_attendance)
    ItemButton mBtnAttendance;
    @BindView(R.id.btn_leave)
    ItemButton mBtnLeave;
    @BindView(R.id.btn_whereabouts)
    ItemButton mBtnWhereabouts;
    @BindView(R.id.rv_application)
    RecyclerView mRvApplication;
    private MainApplicationAdapter mApplicationAdapter;
    private int mRoleId;
    private UserInfoBean mUserInfo;

    @Override
    protected void initListener() {
        mApplicationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id = mApplicationAdapter.getData().get(position).getId();
                switch (id) {
                    case 5:
                        startActivity(new Intent(mContext, OrderFoodActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, AttendanceClockActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, ApprovalActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, NewsActivity.class));
                        break;
                    case 6:
                        if (mRoleId == 2 || mRoleId == 8) {
                            ToastUtils.showShort("账号无权限查看！");
                            return;
                        }
                        startActivity(new Intent(mContext, ManagerOrderActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(mContext, MattersApplyActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void loadData() {
        UserIdApiKeyParams params = new UserIdApiKeyParams(mUserInfo.getUserId(), mUserInfo.getApikey());
        HttpHelper.getInstance().getAppSomeCount(params, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                GetAppSomeCountBean resultBean = JSON.parseObject(json, GetAppSomeCountBean.class);
                mBtnApproval.setIconText(resultBean.getPendCount());
                mBtnAttendance.setIconText(resultBean.getFactAttendDay());
            }

            @Override
            public void onFailure(Call call, String message) {
ToastUtils.showShort("获取出勤天数和和待审批申请数量失败，"+message);
            }
        });
    }

    @Override
    protected void initData() {
        //initApplicationList
        mRvApplication.setLayoutManager(new GridLayoutManager(mContext, 4));
        mApplicationAdapter = new MainApplicationAdapter(R.layout.item_common_application);
        mRvApplication.setAdapter(mApplicationAdapter);
        ArrayList<ApplicationBean> applicationBeans = new ArrayList<>();
        applicationBeans.add(new ApplicationBean(1, "通知公告", R.drawable.gonggao));
        applicationBeans.add(new ApplicationBean(2, "审批", R.drawable.shenpi));
        //        applicationBeans.add(new ApplicationBean(3, "考勤打卡", R.drawable.kaoqing_2));
        //        applicationBeans.add(new ApplicationBean(4, "公文流传", R.drawable.gongwen));
        applicationBeans.add(new ApplicationBean(5, "订餐", R.drawable.dingcan_2));
        applicationBeans.add(new ApplicationBean(6, "订单管理", R.drawable.dingcan_order));
        applicationBeans.add(new ApplicationBean(7, "事项申请", R.drawable.sxsq));
        mApplicationAdapter.setNewData(applicationBeans);
        SPUtils spUtils = SPUtils.getInstance(SpConsts.FILE_PERSONAL_INFORMATION);
        mRoleId = spUtils.getInt(SpConsts.KEY_ROLE_ID);
        int corner = (int) (getResources().getDimension(R.dimen.dp_10) + 0.5f);
        RequestOptions bannerImageOptions = new RequestOptions().transforms(new CenterCrop(),new RoundedCorners(corner));
        Glide.with(this).load(R.drawable.banner).apply(bannerImageOptions).into(mIvBanner);
        mUserInfo = OaSpUtil.getUserInfo();
    }

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_work;
    }

    @OnClick({R.id.btn_approval, R.id.btn_attendance, R.id.btn_leave, R.id.btn_whereabouts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_approval:
                startActivity(new Intent(mContext, ApprovalActivity.class));
                break;
            case R.id.btn_attendance:
                startActivity(new Intent(mContext, MyAttendanceActivity.class));
                break;
            case R.id.btn_leave:
                startActivity(new Intent(mContext, LeaveActivity.class));
                break;
            case R.id.btn_whereabouts:
                startActivity(new Intent(mContext, StaffDestinationActivity.class));
                break;
        }
    }
}
