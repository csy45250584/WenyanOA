package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class HandlerApprovalParams extends UserIdApiKeyParams {
    private int id;
    private String refusefor;

    public HandlerApprovalParams(int userId, String apiKey, int id, String refusefor) {
        super(userId, apiKey);
        this.id = id;
        this.refusefor = refusefor;
    }
}
