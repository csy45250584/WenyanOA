package com.haokuo.wenyanoa.network.bean.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class UserIdApiKeyParams implements IGetApiKey {
    private int userId;
    private String apiKey;

    public UserIdApiKeyParams(int userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }
}
