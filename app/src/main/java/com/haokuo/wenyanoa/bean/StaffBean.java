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

    public StaffBean(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public StaffBean() {
    }
}
