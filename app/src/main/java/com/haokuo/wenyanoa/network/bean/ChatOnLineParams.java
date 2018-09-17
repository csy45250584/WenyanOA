package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/9/12.
 */
@Data
public class ChatOnLineParams extends UserIdApiKeyParams {
    private int fromId;
    private int toId;

    public ChatOnLineParams(int userId, String apiKey, int fromId, int toId) {
        super(userId, apiKey);
        this.fromId = fromId;
        this.toId = toId;
    }
}
