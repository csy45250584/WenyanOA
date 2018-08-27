package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class GetInFoodListParams extends UserIdApiKeyParams {
    private int foodlistType;
    private int salesStatus;

    public GetInFoodListParams(int userId, String apiKey, int foodlistType, int salesStatus) {
        super(userId, apiKey);
        this.foodlistType = foodlistType;
        this.salesStatus = salesStatus;
    }
}
