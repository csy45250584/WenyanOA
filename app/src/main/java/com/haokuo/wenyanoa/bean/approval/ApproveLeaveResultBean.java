package com.haokuo.wenyanoa.bean.approval;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class ApproveLeaveResultBean {

    private int count;
    private List<LeaveBean> data;

    @Data
    public static class LeaveBean extends GetIdBean{
        private String appStatus; //审批状态
        private int state; //审批状态
        private String realname; //创建人
        private String startDate; //请假开始时间
        private String endDate; //请假结束时间
        private String fillformDate; //创建时间
        private UserBean user;
        private ApprovalUserInfoBean userInfo;
    }
}
