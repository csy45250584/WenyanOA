package com.haokuo.wenyanoa.bean;

import com.haokuo.wenyanoa.bean.approval.ApprovalUserInfoBean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class StaffDestinationResultBean {
    private int count;
    private List<StaffDestinationBean> data;

    @Data
    public static class StaffDestinationBean {
        private String name;
        private String endDate;
        private String createDate;
        private int id;
        private String secition;
        private String location;
        private String sex;
        private String startDate;
        private int userinfoId;
        private ApprovalUserInfoBean userInfo;
    }
}
