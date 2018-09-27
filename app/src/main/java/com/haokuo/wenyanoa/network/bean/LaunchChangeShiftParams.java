package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class LaunchChangeShiftParams extends MatterBaseParams {
    private String oldWorkDate; //原上班时间
    private String newWorkDate; //调动上班时间
    private int transferId; //换班人userId
    private String incident; //换班事由
    private int nowlevelId; //当前审批人

    public LaunchChangeShiftParams(int userId, String apiKey, Integer onelevelId, Integer twolevelId, Integer threelevelId, Integer courtesyCopyId, String oldWorkDate, String newWorkDate, int transferId, String incident, int nowlevelId) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.oldWorkDate = oldWorkDate;
        this.newWorkDate = newWorkDate;
        this.transferId = transferId;
        this.incident = incident;
        this.nowlevelId = nowlevelId;
    }
}
