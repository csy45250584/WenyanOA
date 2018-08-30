package com.haokuo.wenyanoa.bean.approval;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class ApproveApplyItemsResultBean {

    private int count;
    private List<ApplyItemsBean> data;

    @Data
    public static class ApplyItemsBean extends GetIdBean {
        private int state; //审批状态
        private String appState; //审批状态
        private String realname; //创建人
        private String fillformDate; //创建时间
        private String items_name; //申领物品
        private String incident; //用途
        private UserBean user;
        private ApprovalUserInfoBean userInfo;
    }
}
