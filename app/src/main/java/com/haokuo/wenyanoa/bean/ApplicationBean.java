package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-06.
 */
@Data
public class ApplicationBean {
    private int id;
    private String title;
    private int iconSrc;

    public ApplicationBean(int id, String title, int iconSrc) {
        this.id = id;
        this.title = title;
        this.iconSrc = iconSrc;
    }
}
