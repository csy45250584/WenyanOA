package com.haokuo.wenyanoa.eventbus;

import com.haokuo.wenyanoa.bean.ApplicationBean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-07.
 */
@Data
public class ApprovalAppClickEvent {
    private ApplicationBean applicationBean;

    public ApprovalAppClickEvent(ApplicationBean applicationBean) {
        this.applicationBean = applicationBean;
    }
}
