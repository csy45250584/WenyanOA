package com.haokuo.wenyanoa.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-08.
 */
@Getter
@Setter
public class DishesBean {
    private String name;
    private String imageUrl;
    private double price;
    private int count;
    private String time;
    private boolean isChecked;



    public DishesBean() {
    }
}
