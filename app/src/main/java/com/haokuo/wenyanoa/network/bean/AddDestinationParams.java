package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class AddDestinationParams extends UserIdApiKeyParams {
    private int id;
    private int userinfoId;
    private String startDate;
    private String endDate;
    private String location;

    public AddDestinationParams(int userId, String apiKey, int id, int userinfoId, String startDate, String endDate, String location) {
        super(userId, apiKey);
        this.id = id;
        this.userinfoId = userinfoId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }
}
