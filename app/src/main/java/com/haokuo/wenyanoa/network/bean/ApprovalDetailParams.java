package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class ApprovalDetailParams extends UserIdApiKeyParams {
    private int id;

    public ApprovalDetailParams(int userId, String apiKey, int id) {
        super(userId, apiKey);
        this.id = id;
    }
}
