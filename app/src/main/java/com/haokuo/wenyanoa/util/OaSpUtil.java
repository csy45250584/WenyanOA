package com.haokuo.wenyanoa.util;

import com.haokuo.wenyanoa.bean.UserInfoBean;
import com.haokuo.wenyanoa.consts.SpConsts;
import com.haokuo.wenyanoa.util.utilscode.SPUtils;

/**
 * Created by zjf on 2018-08-17.
 */

public class OaSpUtil {
    public static UserInfoBean getUserInfo() {
        SPUtils utils = SPUtils.getInstance(SpConsts.FILE_PERSONAL_INFORMATION);
        int userId = utils.getInt(SpConsts.KEY_USER_ID, -1);
        String apiKey = utils.getString(SpConsts.KEY_API_KEY);
        String username = utils.getString(SpConsts.KEY_USERNAME);
        return new UserInfoBean(userId, apiKey, username);
    }
}
