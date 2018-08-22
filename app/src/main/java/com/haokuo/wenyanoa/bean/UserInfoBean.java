package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-17.
 */
@Data
public class UserInfoBean {
    private int userId;
    private String apiKey;
    private String username;

    public UserInfoBean(int userId, String apiKey, String username) {
        this.userId = userId;
        this.apiKey = apiKey;
        this.username = username;
    }
}
