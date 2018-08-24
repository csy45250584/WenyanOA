package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class LaunchLeaveParams extends MatterBaseParams {
    private String leaveType; //事假类别
    private String incident; //请假事由
    private String startDate; //开始时间
    private String endDate; //结束时间
    private String howlong; //请假时长

    public LaunchLeaveParams(int userId, String apiKey, int onelevelId, int twolevelId, int threelevelId, int courtesyCopyId, String leaveType, String incident, String startDate, String endDate, String howlong) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.leaveType = leaveType;
        this.incident = incident;
        this.startDate = startDate;
        this.endDate = endDate;
        this.howlong = howlong;
    }
}
