package com.haokuo.wenyanoa.bean;

import android.support.annotation.NonNull;

import com.haokuo.wenyanoa.util.utilscode.PinyinUtils;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/21.
 */
@Data
public class ContactResultBean {
    private String total;
    private List<ContactBean> data;

    @Data
    public static class ContactBean implements Serializable, Comparable<ContactResultBean.ContactBean> {
        private int id;
        private String realname;
        private String telphone;
        private String mobilePhone;
        private String sectionPhone;
        private String job;
        private String secition;
        private String sex;
        private String headPhoto;
        private String namePinyin;
        private String firstLetter;

        @Override
        public int compareTo(@NonNull ContactResultBean.ContactBean another) {
            return this.namePinyin.compareToIgnoreCase(another.getNamePinyin());
        }

        public void setRealname(String realname) {
            this.realname = realname;
            this.namePinyin = PinyinUtils.ccs2Pinyin(realname).toUpperCase();
            this.firstLetter = PinyinUtils.getPinyinFirstLetter(realname).toUpperCase();
        }
    }
}
