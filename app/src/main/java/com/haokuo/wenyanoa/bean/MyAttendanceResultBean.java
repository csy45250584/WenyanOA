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
        private int id;
        private String onDutyDate; //上班日期
        private String minAttend; //出勤时长
        private String clockOnTime; //上班打卡时间
        private String onStatus; //上班状态
        private String onTime; //上班时间
        private String clockOffTime; //下班打卡时间
        private String offStatus; //下班状态
        private String offTime; //下班时间
        private String minuteLate; //迟到时长（已有在状态为“迟到”时才有）
        private String minLeave; //早退时长（已有在状态为“迟到”时才有）

        public void setClockOnTime(String clockOnTime) {
            String[] splits = clockOnTime.split(" ");
            if (splits.length > 1) {
                this.clockOnTime = splits[1];
            } else {
                this.clockOnTime = clockOnTime;
            }
        }

        public void setClockOffTime(String clockOffTime) {
            String[] splits = clockOffTime.split(" ");
            if (splits.length > 1) {
                this.clockOffTime = splits[1];
            } else {
                this.clockOffTime = clockOffTime;
            }
        }

        public void setOnTime(String onTime) {
            String[] splits = onTime.split(" ");
            if (splits.length > 1) {
                this.onTime = splits[1];
            } else {
                this.onTime = onTime;
            }
        }

        public void setOffTime(String offTime) {
            String[] splits = offTime.split(" ");
            if (splits.length > 1) {
                this.offTime = splits[1];
            } else {
                this.offTime = offTime;
            }
        }
    }
}
