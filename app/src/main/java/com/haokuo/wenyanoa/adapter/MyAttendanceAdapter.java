package com.haokuo.wenyanoa.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.MyAttendanceResultBean;
import com.rey.material.widget.TextView;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class MyAttendanceAdapter extends BaseQuickAdapter<MyAttendanceResultBean.AttendanceBean, BaseViewHolder> {

    private  int mStateStrokeWith;

    public MyAttendanceAdapter(int layoutResId) {
        super(layoutResId);
        mStateStrokeWith = (int) (mContext.getResources().getDimension(R.dimen.dp_1) + 0.5f);
        int color = ContextCompat.getColor(mContext, android.R.color.holo_red_light);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MyAttendanceResultBean.AttendanceBean item) {
        helper.setText(R.id.tv_attendance_day, String.format("%s的考勤", item.getOnDutyDate()));
        helper.setText(R.id.tv_time_to_work, String.format("上班时间:       %s", item.getOnTime()));
        helper.setText(R.id.tv_time_off_work, String.format("下班时间:       %s", item.getOffTime()));
        helper.setText(R.id.tv_time_clock_in, String.format("上班打卡时间:       %s", item.getClockOnTime()));
        helper.setText(R.id.tv_time_clock_out, String.format("下班打卡时间:       %s", item.getClockOffTime()));
        String attendTimeString = buildAttendTimeString(item.getMinAttend());
        helper.setText(R.id.tv_attendance_time, String.format("出勤时长：     %s", attendTimeString));
        GradientDrawable onBackground = (GradientDrawable) ((TextView) helper.getView(R.id.tv_attendance_on_state)).getBackground();
        onBackground.setStroke(mStateStrokeWith,);
        TextView tvAttendanceOffState = helper.getView(R.id.tv_attendance_off_state);
        boolean onNormal = item.getOnStatus().endsWith("正常上班");
        boolean offNormal = item.getOffStatus().endsWith("正常下班");
    }

    private String buildAttendTimeString(String minAttendString) {
        int minAttend = Integer.parseInt(minAttendString);
        int hour = minAttend / 60;
        int min = minAttend % 60;
        StringBuilder stringBuilder = new StringBuilder();
        if (hour != 0) {
            stringBuilder.append(hour).append("小时");
        }
        return stringBuilder.append(min).append("分钟").toString();
    }
}
