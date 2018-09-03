package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class SaveFoodInBasketParams extends UserIdApiKeyParams {
    private int disheId;
    private int num;
    private int salesStatus;
    private String eatTime;

    public SaveFoodInBasketParams(int userId, String apiKey, int disheId, int num, int salesStatus, String eatTime) {
        super(userId, apiKey);
        this.disheId = disheId;
        this.num = num;
        this.salesStatus = salesStatus;
        this.eatTime = eatTime;
    }
}
