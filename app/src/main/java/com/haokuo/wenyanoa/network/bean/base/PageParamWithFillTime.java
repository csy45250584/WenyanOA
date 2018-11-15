package com.haokuo.wenyanoa.network.bean.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class PageParamWithFillTime extends UserIdApiKeyParams {
    private int pageIndex;
    private int pageSize;
    private String fillformDate;//格式2018-10
    private int matterType;

    public PageParamWithFillTime(int userId, String apiKey, int pageIndex, int pageSize, int matterType, String fillformDate) {
        super(userId, apiKey);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.matterType = matterType;
        this.fillformDate = fillformDate;
    }

    public void resetPageIndex() {
        pageIndex = 0;
    }

    public void increasePageIndex() {
        pageIndex++;
    }
}
