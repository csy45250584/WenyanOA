package com.haokuo.wenyanoa.network.bean.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zjf on 2018-08-15.
 */
@Getter
@Setter
public class PageParams extends UserIdApiKeyParams {
    private int pageIndex;
    private int pageSize;

    public PageParams(int userId, String apiKey, int pageIndex, int pageSize) {
        super(userId, apiKey);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public void resetPageIndex() {
        pageIndex = 0;
    }
    public void increasePageIndex() {
        pageIndex++;
    }
}
