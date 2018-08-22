package com.haokuo.wenyanoa.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.MyAttendanceResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class MyAttendanceAdapter extends BaseQuickAdapter<MyAttendanceResultBean.AttendanceBean, BaseViewHolder> {

    private int mStateStrokeWith;
    private final int mColorNormal;
    private final int mColorAbnormal;

    public MyAttendanceAdapter(int layoutResId, Context context) {
        super(layoutResId);
        mStateStrokeWith = (int) (context.getResources().getDimension(R.dimen.dp_1) + 0.5f);
        mColorNormal = ContextCompat.getColor(context, android.R.color.holo_green_light);
        mColorAbnormal = ContextCompat.getColor(context, android.R.color.holo_red_light);
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
        //上班状态标志
        String onStatus = item.getOnStatus();
        boolean onNormal = onStatus.equals("正常上班");
        TextView tvAttendanceOnState = helper.getView(R.id.tv_attendance_on_state);
        if (onStatus.equals("迟到")) {
            onStatus = onStatus + item.getMinuteLate() + "分钟";
        } else if (onStatus.equals("早退")) {

            onStatus = onStatus + item.getMinLeave() + "分钟";
        }
        tvAttendanceOnState.setText(onStatus);
        GradientDrawable onBackground = (GradientDrawable) tvAttendanceOnState.getBackground();
        onBackground.setStroke(mStateStrokeWith, onNormal ? mColorNormal : mColorAbnormal);
        tvAttendanceOnState.setBackground(onBackground);
        tvAttendanceOnState.setTextColor(onNormal ? mColorNormal : mColorAbnormal);
        //下班状态标志
        String offStatus = item.getOffStatus();
        TextView tvAttendanceOffState = helper.getView(R.id.tv_attendance_off_state);
        boolean offNormal = offStatus.equals("正常下班");
        if (offStatus.equals("迟到")) {
            offStatus = offStatus + item.getMinuteLate() + "分钟";
        } else if (offStatus.equals("早退")) {
            offStatus = offStatus + item.getMinLeave() + "分钟";
        }
        tvAttendanceOffState.setText(offStatus);
        GradientDrawable offBackground = (GradientDrawable) tvAttendanceOffState.getBackground();
        offBackground.setStroke(mStateStrokeWith, offNormal ? mColorNormal : mColorAbnormal);
        tvAttendanceOffState.setBackground(offBackground);
        tvAttendanceOffState.setTextColor(offNormal ? mColorNormal : mColorAbnormal);
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
