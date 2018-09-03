package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class BasketListResultBean {

    private int count;
    private int pageIndex;
    private int pageSize;
    private List<BasketBean> data;
@Data
    public static class BasketBean {
        private String coverImage;
        private String createDate;
        private String eatTime;
        private String foodName;
        private double foodPrice;
        private int id;
        private int num;
        private int salesStatus;
        private int disheId;
        private int userId;
        private boolean isChecked;
    }
}
