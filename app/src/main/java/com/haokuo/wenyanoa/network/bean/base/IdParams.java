package com.haokuo.wenyanoa.network.bean.base;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class IdParams extends UserIdApiKeyParams {
    private int id;

    public IdParams(int userId, String apiKey, int id) {
        super(userId, apiKey);
        this.id = id;
    }
}