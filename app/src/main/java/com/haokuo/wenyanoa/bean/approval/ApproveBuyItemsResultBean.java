package com.haokuo.wenyanoa.bean.approval;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class ApproveBuyItemsResultBean {

    private int count;
    private List<BuyItemsBean> data;

    @Data
    public static class BuyItemsBean {
        private int id; //id
        private int state; //审批状态
        private String appStatus; //审批状态
        private String realname; //创建人
        private String fillformDate; //创建时间

        private String buyItems; //申购物品
    }
}
