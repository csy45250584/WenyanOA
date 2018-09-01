package com.haokuo.wenyanoa.bean;

import org.litepal.crud.LitePalSupport;

import lombok.Data;

/**
 * Created by zjf on 2018/9/1.
 */
@Data
public class BasketDishesBean extends LitePalSupport {
    private final double totalPrice;
    private long id;
    private String foodName;
    private String coverImage;
    private boolean isChecked;
    private int count;
    /** 0早餐 1中餐 2晚餐 */
    private int diningMeal;
    private String diningMealString;
    private String diningWeekday;
    private String diningDate;
    private int userId;
    private long dishId;

    public BasketDishesBean(String foodName, String coverImage, double totalPrice, boolean isChecked, int count, int diningMeal, String diningMealString, String diningWeekday, String diningDate, int userId) {
        this.foodName = foodName;
        this.coverImage = coverImage;
        this.totalPrice = totalPrice;
        this.isChecked = isChecked;
        this.count = count;
        this.diningMeal = diningMeal;
        this.diningMealString = diningMealString;
        this.diningWeekday = diningWeekday;
        this.diningDate = diningDate;
        this.userId = userId;
    }
}
