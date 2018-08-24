package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class LaunchTripParams extends MatterBaseParams {
    private String trip_place; //公差地
    private String incident; //公差事由
    private String startDate; //开始时间
    private String endDate; //结束时间
    private String howlong; //公差时长

    public LaunchTripParams(int userId, String apiKey, int onelevelId, int twolevelId, int threelevelId, int courtesyCopyId, String trip_place, String incident, String startDate, String endDate, String howlong) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.trip_place = trip_place;
        this.incident = incident;
        this.startDate = startDate;
        this.endDate = endDate;
        this.howlong = howlong;
    }
}
