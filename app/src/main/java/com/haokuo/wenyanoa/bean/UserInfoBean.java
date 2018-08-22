package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-17.
 */
@Data
public class UserInfoBean {
    private int userId;
    private String userName;
    private String apikey;

    public UserInfoBean(int userId, String userName, String apikey) {
        this.userId = userId;
        this.userName = userName;
        this.apikey = apikey;
    }
}
