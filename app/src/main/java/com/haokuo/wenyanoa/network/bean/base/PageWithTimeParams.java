package com.haokuo.wenyanoa.network.bean.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018/8/21.
 */
@Getter
@Setter
public class PageWithTimeParams extends PageParams {
    private String startTime;
    private String endTime;

    public PageWithTimeParams(int userId, String apiKey, int pageIndex, int pageSize, String startTime, String endTime) {
        super(userId, apiKey, pageIndex, pageSize);
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
