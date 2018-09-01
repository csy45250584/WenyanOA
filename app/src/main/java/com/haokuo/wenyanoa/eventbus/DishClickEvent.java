package com.haokuo.wenyanoa.eventbus;

import com.haokuo.wenyanoa.bean.GetFoodListResultBean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-07.
 */
@Data
public class DishClickEvent {

    private int weekday;
    private String selectedDate;
    private GetFoodListResultBean.DishesBean dishesBean;

    public DishClickEvent(GetFoodListResultBean.DishesBean dishesBean, int weekday, String selectedDate) {
        this.dishesBean = dishesBean;
        this.selectedDate = selectedDate;
        this.weekday = weekday;
    }
}
