package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.PageParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class GetInFoodListParams extends PageParams {
    private int foodlistType;

    public GetInFoodListParams(int userId, String apiKey, int pageIndex, int pageSize, int foodlistType) {
        super(userId, apiKey, pageIndex, pageSize);
        this.foodlistType = foodlistType;
    }
}
