package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018-08-10.
 */
@Data
public class ApplicationSubjectBean {
    private String title;
    private List<ApplicationBean> appList;

    public ApplicationSubjectBean(String title, List<ApplicationBean> appList) {
        this.title = title;
        this.appList = appList;
    }
}
