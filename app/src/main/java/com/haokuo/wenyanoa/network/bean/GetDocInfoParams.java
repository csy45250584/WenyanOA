package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

/**
 * Created by zjf on 2018/9/4.
 */
public class GetDocInfoParams extends UserIdApiKeyParams {
    private int bumfId;

    public GetDocInfoParams(int userId, String apiKey, int bumfId) {
        super(userId, apiKey);
        this.bumfId = bumfId;
    }
}
