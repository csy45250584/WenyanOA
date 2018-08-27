package com.haokuo.wenyanoa.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class NewsResultBean {

    private List<NewsBean> data;

    @Data
    public static class NewsBean {
        private String content;
        private String createDate;
        private String creator;
        private int id;
    }
}
