package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class UpdatePasswordParams extends UserIdApiKeyParams {

    private String oldPass;
    private String newPass;

    public UpdatePasswordParams(int userId, String apiKey, String oldPass, String newPass) {
        super(userId, apiKey);
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
}
