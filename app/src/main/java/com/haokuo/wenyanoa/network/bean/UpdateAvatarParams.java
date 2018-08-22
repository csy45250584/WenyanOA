package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class UpdateAvatarParams extends UserIdApiKeyParams {
    private String picBody;
    private String picName;

    public UpdateAvatarParams(int userId, String apiKey, String picBody, String picName) {
        super(userId, apiKey);
        this.picBody = picBody;
        this.picName = picName;
    }
}
