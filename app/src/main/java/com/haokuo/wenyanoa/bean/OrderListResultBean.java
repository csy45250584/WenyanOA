package com.haokuo.wenyanoa.bean;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
        private String telPhone; //电话
        private List<DishBean> dishes; //下单人
        private long id;

        public String getTotalPrice() {
            BigDecimal totalPrice = new BigDecimal("0");
            for (DishBean dish : dishes) {
                totalPrice = totalPrice.add(BigDecimal.valueOf(dish.getFoodPrice()).multiply(BigDecimal.valueOf(dish.getNum())));
            }
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
            return currencyInstance.format(totalPrice);
        }
    }

    @Data
    public static class DishBean {
        private long id;
        private String coverImage;
        private String foodName;
        private double foodPrice;
        private int num;
    }
}
