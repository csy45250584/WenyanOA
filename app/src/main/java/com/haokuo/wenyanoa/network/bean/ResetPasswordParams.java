package com.haokuo.wenyanoa.network.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class ResetPasswordParams {
    private String telphone;
    private String authCode;
    private String password;

    public ResetPasswordParams(String telphone, String authCode, String password) {
        this.telphone = telphone;
        this.authCode = authCode;
        this.password = password;
    }
}
