package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class GetNoticeInfoParams extends UserIdApiKeyParams {
    private int newsSortId;

    public GetNoticeInfoParams(int userId, String apiKey, int newsSortId) {
        super(userId, apiKey);
        this.newsSortId = newsSortId;
    }
}
