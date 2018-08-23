package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class GetNewsInfoParams extends UserIdApiKeyParams {
    private int newsId;

    public GetNewsInfoParams(int userId, String apiKey, int newsId) {
        super(userId, apiKey);
        this.newsId = newsId;
    }
}
