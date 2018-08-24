package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class LaunchRepairParams extends MatterBaseParams {
    private String expectfixDate; //期望时间
    private String fixItems; //维修物品
    private String damage; //损坏情况
    private String fixAddress; //维修地址
    private String notes; //备注

    public LaunchRepairParams(int userId, String apiKey, int onelevelId, int twolevelId, int threelevelId, int courtesyCopyId, String expectfixDate, String fixItems, String damage, String fixAddress, String notes) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.expectfixDate = expectfixDate;
        this.fixItems = fixItems;
        this.damage = damage;
        this.fixAddress = fixAddress;
        this.notes = notes;
    }
}
