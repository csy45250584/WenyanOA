package com.haokuo.wenyanoa.network.bean.base;

import lombok.Data;

/**
 * Created by zjf on 2018-08-15.
 */
@Data
public class UserIdApiKeyParams implements IGetApiKey {
    private int userId;
    private String apiKey;

    public UserIdApiKeyParams(int userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }
}
