package com.haokuo.wenyanoa.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.util.utilscode.ToastUtils;
import com.haokuo.wenyanoa.view.ClockInView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import moe.feng.common.stepperview.VerticalStepperItemView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by zjf on 2018-08-09.
 */

@RuntimePermissions
public class AttendanceClockActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_view_statistics)
    TextView mTvViewStatistics;
    @BindView(R.id.tv_work_time)
    TextView mTvWorkTime;
    @BindView(R.id.tv_off_work_time)
    TextView mTvOffWorkTime;
    @BindView(R.id.stepper_clock_in)
    VerticalStepperItemView mStepperClockIn;
    @BindView(R.id.stepper_clock_out)
    VerticalStepperItemView mStepperClockOut;
    @BindView(R.id.clock_in_view)
    ClockInView mClockInView;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_attendance_clock;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mStepperClockIn.bindSteppers(null, mStepperClockOut);
        //网络请求准备数据
        initLocation();
        AttendanceClockActivityPermissionsDispatcher.startLocationWithPermissionCheck(this);
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调
        AMapLocationListener locationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        double longitude = aMapLocation.getLongitude(); //经度
                        double latitude = aMapLocation.getLatitude(); //纬度
                        DPoint currentPoint = new DPoint(latitude, longitude);
                        DPoint currentPoint2 = new DPoint(latitude + 0.01, longitude + 0.01);
                        float distance = CoordinateConverter.calculateLineDistance(currentPoint, currentPoint2);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表网址如下：
                        //https://lbs.amap.com/api/android-location-sdk/guide/utilities/errorcode
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        ToastUtils.showShort("当前定位失败！");
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener);
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setInterval(2000);
        mLocationClient.setLocationOption(locationOption);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            mLocationClient = null;
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_view_statistics, R.id.clock_in_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_view_statistics:
                startActivity(new Intent(this, MyAttendanceActivity.class));
                break;

            case R.id.clock_in_view:
                if (mClockInView.isCanClock()) {
                    //可以打卡
                    if (mStepperClockIn.getState() != VerticalStepperItemView.STATE_DONE) {
                        mStepperClockIn.setSummary("fdfdfdfdf");
                        mStepperClockIn.nextStep();
                    } else {
                        mStepperClockOut.setState(VerticalStepperItemView.STATE_DONE);
                        mStepperClockOut.setSummary("fdfdfdfdf");
                    }
                }
                break;
        }
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void startLocation() {
        mLocationClient.startLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AttendanceClockActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showLocationTips(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("打卡功能将会使用到手机定位功能，点击下一步授予应用定位权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        }).show();
    }
}
