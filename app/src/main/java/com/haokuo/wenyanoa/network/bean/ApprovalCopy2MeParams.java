package com.haokuo.wenyanoa.network.bean;

import com.haokuo.wenyanoa.network.bean.base.PageParams;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class ApprovalCopy2MeParams extends PageParams {
    private int matterType;

    public ApprovalCopy2MeParams(int userId, String apiKey, int pageIndex, int pageSize, int matterType) {
        super(userId, apiKey, pageIndex, pageSize);
        this.matterType = matterType;
    }
}
