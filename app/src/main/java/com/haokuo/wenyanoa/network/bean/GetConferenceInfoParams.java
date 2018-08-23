package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class GetConferenceInfoParams extends UserIdApiKeyParams {
    private int conferenceId;

    public GetConferenceInfoParams(int userId, String apiKey, int conferenceId) {
        super(userId, apiKey);
        this.conferenceId = conferenceId;
    }
}
