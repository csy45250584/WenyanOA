package com.haokuo.wenyanoa.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018-08-17.
 */
@Data
public class PrepareMatterResultBean {
    private Integer oneLevelId;
    private Integer twoLevelId;
    private Integer threeLevelId;
    private String oneLevelN;
    private String twoLevelN;
    private String threeLevelN;
    private String oneLevelP;
    private String twoLevelP;
    private String threeLevelP;
    private String oneLeveSex;
    private String twoLeveSex;
    private String threeLeveSex;
    private Integer CourtesyCopyId;
    private String CourtesyCopySex;
    private String CourtesyCopylN;
    private String CourtesyCopylP;

    public List<StaffBean> getApproverList() {
        ArrayList<StaffBean> approverBeans = new ArrayList<>();
        if (oneLevelId != null && oneLevelId != 0) {
            approverBeans.add(new StaffBean(oneLevelId, oneLevelN, oneLevelP, oneLeveSex));
        }
        if (twoLevelId != null && twoLevelId != 0) {
            approverBeans.add(new StaffBean(twoLevelId, twoLevelN, twoLevelP, twoLeveSex));
        }
        if (threeLevelId != null && threeLevelId != 0) {
            approverBeans.add(new StaffBean(threeLevelId, threeLevelN, threeLevelP, threeLeveSex));
        }
        return approverBeans;
    }

    public StaffBean getCc() {
        return new StaffBean(CourtesyCopyId, CourtesyCopylN, CourtesyCopylP, CourtesyCopySex);
    }
}
