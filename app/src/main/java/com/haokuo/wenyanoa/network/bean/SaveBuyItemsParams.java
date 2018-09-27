package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.MatterBaseParams;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class SaveBuyItemsParams extends MatterBaseParams {
    private String buyItems; //申购物品
    private String incident; //备注

    public SaveBuyItemsParams(int userId, String apiKey, Integer onelevelId, Integer twolevelId, Integer threelevelId, Integer courtesyCopyId, String buyItems, String incident) {
        super(userId, apiKey, onelevelId, twolevelId, threelevelId, courtesyCopyId);
        this.buyItems = buyItems;
        this.incident = incident;
    }
}
