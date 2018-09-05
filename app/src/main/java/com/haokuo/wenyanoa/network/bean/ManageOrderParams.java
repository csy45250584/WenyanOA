package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.PageWithTimeParams;

import lombok.Data;

/**
 * Created by zjf on 2018/9/5.
 */
@Data
public class ManageOrderParams extends PageWithTimeParams {
    private String realname;

    public ManageOrderParams(int userId, String apiKey, int pageIndex, int pageSize, String startTime, String endTime, String realname) {
        super(userId, apiKey, pageIndex, pageSize, startTime, endTime);
        this.realname = realname;
    }
}
