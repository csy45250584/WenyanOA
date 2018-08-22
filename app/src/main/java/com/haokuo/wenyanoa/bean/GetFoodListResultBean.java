package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-09.
 */
@Data
public class GetFoodListResultBean {
    private String coverImage;
    private String foodName;
    private double foodPrice;
    private String foodlistType;
    private int id;
    private int salesStatus;
    private String salesStatusApply;
}
