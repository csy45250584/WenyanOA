package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class LaunchApplyItemsParams extends MatterBaseParams {
    private String items_name; //领用物品名字
    private String incident; //物品领用及详情

    public LaunchApplyItemsParams(int userId, String apiKey, Integer onelevelId, Integer twolevelId, Integer threelevelId, Integer courtesyCopyId, String items_name, String incident) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.items_name = items_name;
        this.incident = incident;
    }
}
