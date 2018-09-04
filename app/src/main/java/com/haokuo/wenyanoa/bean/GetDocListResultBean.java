package com.haokuo.wenyanoa.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/9/3.
 */
@Data
public class GetDocListResultBean {
    private int count;
    private List<DocBean> data;

    @Data
    public static class DocBean implements Serializable {
        private String sendDate;
        private int  bumfType;
        private long  id;
        private int  urgentLevel;
    }
}
