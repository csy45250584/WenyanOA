package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-08.
 */
@Data
public class DishesBean {
    private String name;
    private String imageUrl;
    private double price;
    private int count;
    private String time;
    private boolean isChecked;
    private String weekday;
    private String diningDate;

    public DishesBean() {
    }
}
