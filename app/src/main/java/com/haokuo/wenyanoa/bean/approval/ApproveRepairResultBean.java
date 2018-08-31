package com.haokuo.wenyanoa.bean.approval;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class ApproveRepairResultBean {

    private int count;
    private List<RepairBean> data;

    @Data
    public static class RepairBean extends GetIdBean{
        private int state; //审批状态
        private String appStatus; //审批状态
        private String realname; //创建人
        private String fillformDate; //创建时间

        private String fixItems; //报修物品
        private String expectfixDate; //期望维修时间
        private String nowSex; //期望维修时间
        private String nowPhoto; //期望维修时间
    }
}
