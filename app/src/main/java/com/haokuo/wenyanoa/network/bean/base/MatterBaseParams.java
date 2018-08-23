package com.haokuo.wenyanoa.network.bean.base;

import com.haokuo.wenyanoa.util.utilscode.TimeUtils;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class MatterBaseParams extends UserIdApiKeyParams {
    /** 填表时间 */
    private String fillformDate;
    private int onelevelId;
    private int twolevelId;
    private int threelevelId;
    private int courtesyCopyId;

    public MatterBaseParams(int userId, String apiKey, int onelevelId, int twolevelId, int threelevelId, int courtesyCopyId) {
        super(userId, apiKey);
        this.fillformDate = TimeUtils.getNowString(TimeUtils.CUSTOM_FORMAT);
        this.onelevelId = onelevelId;
        this.twolevelId = twolevelId;
        this.threelevelId = threelevelId;
        this.courtesyCopyId = courtesyCopyId;
    }
}
