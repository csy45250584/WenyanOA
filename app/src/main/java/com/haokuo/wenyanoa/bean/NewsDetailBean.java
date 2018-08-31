package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018/8/31.
 */
@Data
public class NewsDetailBean {
    private String content;
    private String title;
    private String createDate;
    private String creator;
    private String coverImage;
    private int id;
}
