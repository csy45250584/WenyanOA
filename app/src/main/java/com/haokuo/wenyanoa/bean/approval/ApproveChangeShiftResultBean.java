package com.haokuo.wenyanoa.bean.approval;

import java.util.List;

import lombok.Data;

/**
 * Created by zjf on 2018/8/27.
 */
@Data
public class ApproveChangeShiftResultBean {

    private int count;
    private List<ChangeShiftBean> data;

    @Data
    public static class ChangeShiftBean extends GetIdBean{
        private String oldWorkDate;
        private String newWorkDate;
        private String createDate;
        private String nowPhoto;
        private String transferSex;
        private String realname;
        private int state;
        private String stateString;


        public void setState(int state) {
            this.state = state;
            switch (state) {
                case 0:
                    this.stateString = "未审批";
                    break;
                case 1:
                    this.stateString = "审批中";
                    break;
                case 2:
                    this.stateString = "审批通过";
                    break;
                case 3:
                    this.stateString = "审批已拒绝";
                    break;
            }
        }

        public void setStateString(String stateString) {
        }
    }
}
