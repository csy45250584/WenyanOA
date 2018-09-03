package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class BuyFoodInBasketParams extends UserIdApiKeyParams {
    private int total;
    private String ids;

    public BuyFoodInBasketParams(int userId, String apiKey, int total, String ids) {
        super(userId, apiKey);
        this.total = total;
        this.ids = ids;
    }
}
