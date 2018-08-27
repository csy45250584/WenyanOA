package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018-08-09.
 */
@Data
public class GetFoodListResultBean {
    private List<DishesBean> data;

    @Data
    public static class DishesBean {
        private String foodName;
        private String coverImage;
        private double foodPrice;
        private String foodlistType;
        private int id;
        private int count;
        private boolean isChecked;
        /** 0早餐 1中餐 2晚餐 */
        private int salesStatus;
        private String salesStatusApply;

        public DishesBean() {
        }
    }
}
