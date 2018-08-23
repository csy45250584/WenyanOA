package com.haokuo.wenyanoa.network.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class CodeCheckParams {
    private String telphone;
    private String authCode;

    public CodeCheckParams(String telphone, String authCode) {
        this.telphone = telphone;
        this.authCode = authCode;
    }
}
