package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/21.
 */
@Data
public class MyAttendanceResultBean {

    private String msg;
    private int count;
    private List<AttendanceBean> data;

    @Data
    public static class AttendanceBean {
        private String onDutyDate; //上班日期
        private String minAttend; //出勤时长
        private String clockOnTime; //上班打卡时间
        private String onStatus; //上班状态
        private String onTime; //上班时间
        private String clockOffTime; //下班打卡时间
        private String offStatus; //下班状态
        private String offTime; //下班时间
        private String minuteLate; //迟到时长（已有在状态为“迟到”时才有）
        private String minuteLate; //迟到时长（已有在状态为“迟到”时才有）

        public void setClockOnTime(String clockOnTime) {
            String[] split = clockOnTime.split(" ");
            this.clockOnTime = split[1];
        }

        public void setClockOffTime(String clockOffTime) {
            String[] split = clockOffTime.split(" ");
            this.clockOffTime = split[1];
        }

        public void setOnTime(String onTime) {
            String[] split = onTime.split(" ");
            this.onTime = split[1];
        }

        public void setOffTime(String offTime) {
            String[] split = offTime.split(" ");
            this.offTime = split[1];
        }
    }
}
