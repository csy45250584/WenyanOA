package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class OrderListResultBean {
    private int count;
    private List<OrderBean> data;

    @Data
    public static class OrderBean {
        private String applicationTime; //下单时间
        private String eatTime; //用餐时间
        private String content; //菜品内容
        private String payMethod; //支付方式
        private String realname; //下单人
        private long id;
    }
}
