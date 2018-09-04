package com.haokuo.wenyanoa.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.approval.ApprovalActivity;
import com.haokuo.wenyanoa.activity.AttendanceClockActivity;
import com.haokuo.wenyanoa.activity.ManagerOrderActivity;
import com.haokuo.wenyanoa.activity.NewsActivity;
import com.haokuo.wenyanoa.activity.OrderFoodActivity;
import com.haokuo.wenyanoa.activity.StaffDestinationActivity;
import com.haokuo.wenyanoa.activity.matters.MattersApplyActivity;
import com.haokuo.wenyanoa.adapter.MainApplicationAdapter;
import com.haokuo.wenyanoa.bean.ApplicationBean;
import com.haokuo.wenyanoa.util.BannerImageLoader;
import com.haokuo.wenyanoa.view.ItemButton;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zjf on 2018-07-09.
 */

public class WorkFragment extends BaseLazyLoadFragment {

    @BindView(R.id.banner_work)
    Banner mBannerWork;
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
    protected void initData() {
        //initBanner
        //设置图片加载器
        mBannerWork.setImageLoader(new BannerImageLoader());
        //设置图片集合
        ArrayList<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531283197476&di=66933caadb1b81fe09029d05c834b09b&imgtype=0&src=http%3A%2F%2Fwww.uradiosystems.com%2Fimages%2Fwifi_1.png");
        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=896325921,2567220497&fm=27&gp=0.jpg");
        images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=129596160,3644915266&fm=27&gp=0.jpg");
        images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=49481681,3993225929&fm=27&gp=0.jpg");
        mBannerWork.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBannerWork.start();
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
    }

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_work;
    }

    @OnClick({R.id.btn_approval, R.id.btn_attendance, R.id.btn_leave, R.id.btn_whereabouts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_approval:
                break;
            case R.id.btn_attendance:
                break;
            case R.id.btn_leave:
                break;
            case R.id.btn_whereabouts:
                startActivity(new Intent(mContext, StaffDestinationActivity.class));
                break;
        }
    }
}
