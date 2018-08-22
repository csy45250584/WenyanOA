package com.haokuo.wenyanoa.bean;

import lombok.Data;

/**
 * Created by zjf on 2018-08-17.
 */
@Data
public class UserInfoDetailBean {
    private int userId;
    private String apiKey;
    private String userName;
    private String realname;
    private String sex;
    private String birthday;
    private String telPhone;
    private String address;
    private String qq;
    private int userType;
    private String userTypeName;
    private String userJob;
    private String userSecition;
    private String mobilePhone;
    private String sectionPhone;
    private String weChat;
    private String headPhoto;

    public UserInfoDetailBean(int userId, String apiKey, String userName, String realname, String sex, String birthday, String telPhone, String address, String qq, int userType, String userTypeName, String userJob, String userSecition, String mobilePhone, String sectionPhone, String weChat, String headPhoto) {
        this.userId = userId;
        this.apiKey = apiKey;
        this.userName = userName;
        this.realname = realname;
        this.sex = sex;
        this.birthday = birthday;
        this.telPhone = telPhone;
        this.address = address;
        this.qq = qq;
        this.userType = userType;
        this.userTypeName = userTypeName;
        this.userJob = userJob;
        this.userSecition = userSecition;
        this.mobilePhone = mobilePhone;
        this.sectionPhone = sectionPhone;
        this.weChat = weChat;
        this.headPhoto = headPhoto;
    }
}
