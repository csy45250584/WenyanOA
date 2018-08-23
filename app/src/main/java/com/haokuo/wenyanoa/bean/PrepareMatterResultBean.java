package com.haokuo.wenyanoa.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018-08-17.
 */
@Data
public class PrepareMatterResultBean {
    private int oneLevelId;
    private int twoLevelId;
    private int threeLevelId;
    private String oneLevelN;
    private String twoLevelN;
    private String threeLevelN;
    private String oneLevelP;
    private String twoLevelP;
    private String threeLevelP;

    public List<StaffBean> getApproverList() {
        ArrayList<StaffBean> approverBeans = new ArrayList<>();
        if (oneLevelId != 0) {
            approverBeans.add(new StaffBean(oneLevelId, oneLevelN, oneLevelP));
        }
        if (twoLevelId != 0) {
            approverBeans.add(new StaffBean(twoLevelId, twoLevelN, twoLevelP));
        }
        if (threeLevelId != 0) {
            approverBeans.add(new StaffBean(threeLevelId, threeLevelN, threeLevelP));
        }

        return approverBeans;
    }


}
