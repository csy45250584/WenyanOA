package com.haokuo.wenyanoa.network.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-15.
 */
@Data
public class LoginParams {
    private String userName;
    private String password;

    public LoginParams(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
