package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class LaunchTansferParams extends UserIdApiKeyParams {

    private String oldWorkDate;
    private String newWorkDate;
    private String transferId;
    private String incident;
    private String onelevelId;
    private String twolevelId;
    private String threelevelId;
    private String courtesyCopyId;
    private String nowlevelId;
    private String refusefor;

    public LaunchTansferParams(int userId, String apiKey, String oldWorkDate, String newWorkDate, String transferId, String incident, String onelevelId, String twolevelId, String threelevelId, String courtesyCopyId, String nowlevelId, String refusefor) {
        super(userId, apiKey);
        this.oldWorkDate = oldWorkDate;
        this.newWorkDate = newWorkDate;
        this.transferId = transferId;
        this.incident = incident;
        this.onelevelId = onelevelId;
        this.twolevelId = twolevelId;
        this.threelevelId = threelevelId;
        this.courtesyCopyId = courtesyCopyId;
        this.nowlevelId = nowlevelId;
        this.refusefor = refusefor;
    }
}
