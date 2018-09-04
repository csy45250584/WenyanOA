package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.PageParams;

/**
 * Created by zjf on 2018/9/4.
 */
public class GetDocListParams extends PageParams {
    private int flowType;

    public GetDocListParams(int userId, String apiKey, int pageIndex, int pageSize, int flowType) {
        super(userId, apiKey, pageIndex, pageSize);
        this.flowType = flowType;
    }
}
