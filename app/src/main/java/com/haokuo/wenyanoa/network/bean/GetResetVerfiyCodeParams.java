package com.haokuo.wenyanoa.network.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class GetResetVerfiyCodeParams {
    private String telphone;

    public GetResetVerfiyCodeParams(String telphone) {
        this.telphone = telphone;
    }
}
