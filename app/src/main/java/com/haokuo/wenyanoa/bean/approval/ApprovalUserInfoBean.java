package com.haokuo.wenyanoa.bean.approval;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by zjf on 2018/8/30.
 */
@Data
public class ApprovalUserInfoBean implements Serializable{
    private String realName;
    private String secition;
    private String job;
    private String headPhoto;
    private int userId;
}
