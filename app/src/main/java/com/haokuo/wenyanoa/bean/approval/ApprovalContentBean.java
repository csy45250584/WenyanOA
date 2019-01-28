package com.haokuo.wenyanoa.bean.approval;

import com.haokuo.wenyanoa.bean.StaffBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class ApprovalContentBean {
    //抄送人
    private String copySex;
    private String copylN;
    private String copylP;
    private int courtesyCopyId;
    //审批人
    private String oneLeveSex;
    private String oneLevelN;
    private String oneLevelP;
    private int onelevelId;
    private String twoLeveSex;
    private String twoLevelN;
    private String twoLevelP;
    private int twolevelId;
    private String threeLeveSex;
    private String threeLevelN;
    private String threeLevelP;
    private int threelevelId;
    //所有都有
    private String appStatus;
    private String refusefor; //拒绝理由
    private UserBean user; //取性别
    private ApprovalUserInfoBean userInfo; //取用户信息
    //事假、公差所用
    private String startDate;
    private String endDate;
    private String howlong;
    private String incident;
    private String leaveType;
    //调班所有
    private String newWorkDate;
    private String nowPhoto;
    private String nowSex;
    private String oldWorkDate;
    private int state;
    private String userName;
    private String transferJob;
    private String transferSection;
    private String transferName;
    //申购所有
    private String buyItems;
    private String job;
    private String realname;
    private String fillformDate;
    private String section;
    //报修所用字段
    private String damage;
    private String expectfixDate;
    private String fixAddress;
    private String fixItems;
    //领用多用字段
    private String items_name;
    private String appState;

    public String getState() {
        switch (state) {
            case 0:
                return "未审批";
            case 1:
                return "审批中";
            case 2:
                return "审批通过";
            case 3:
                return "审批已拒绝";
        }
        return null;
    }

    public List<StaffBean> getApproverList() {
        ArrayList<StaffBean> approverBeans = new ArrayList<>();
        if (onelevelId != 0) {
            approverBeans.add(new StaffBean(onelevelId, oneLevelN, oneLevelP, oneLeveSex));
        }
        if (twolevelId != 0) {
            approverBeans.add(new StaffBean(twolevelId, twoLevelN, twoLevelP, twoLeveSex));
        }
        if (threelevelId != 0) {
            approverBeans.add(new StaffBean(threelevelId, threeLevelN, threeLevelP, threeLeveSex));
        }

        return approverBeans;
    }
}
