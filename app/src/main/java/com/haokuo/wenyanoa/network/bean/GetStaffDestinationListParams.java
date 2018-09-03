package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.PageParams;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class GetStaffDestinationListParams extends PageParams {
    private String name;

    public GetStaffDestinationListParams(int userId, String apiKey, int pageIndex, int pageSize, String name) {
        super(userId, apiKey, pageIndex, pageSize);
        this.name = name;
    }
}
