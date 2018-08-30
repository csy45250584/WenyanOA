package com.haokuo.wenyanoa.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by zjf on 2018/8/23.
 */
@Data
public class StaffBean implements Serializable {
    private int id;
    private String name;
    private String avatar;
    private String sex;

    public StaffBean(int id, String name, String avatar,String sex) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.sex = sex;
    }

    public StaffBean() {
    }
}
