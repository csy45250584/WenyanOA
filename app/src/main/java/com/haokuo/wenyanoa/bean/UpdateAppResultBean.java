package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018/9/29.
 */
@Data
public class UpdateAppResultBean {
    private String versionName;
    private int versionCode;
    private String updateContent;
    private String url;
    private long size;
}
