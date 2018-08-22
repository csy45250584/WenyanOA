package com.haokuo.wenyanoa.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjf on 2018-07-21.
 */

public class UrlBuilder {
    private static final String IMAGE_STRING_SPLIT = ",";

    private static final String BASE_URL = "http://192.168.1.92:9079/oai/";
    private static final String LOGIN_URL = "/oaCustom/loginByPassword.do";
    private static final String GET_USER_INFO_URL = "/oaCustom/getUserInfo.do";
    private static final String LAUNCH_TANSFER_URL = "/oaCustom/launchTansfer.do";
    private static final String GET_IN_FOOD_LIST_URL = "/mess/getInFoodList.do";
    private static final String GET_CONFERENCE_URL = "/noteNew/getInConferenceJson.do";
    private static final String GET_IN_BUY_ITEMS_URL = "/oaOffice/getInbuyitems.do";
    private static final String GET_CONTACT_URL = "/oaCustom/getLoginUserContact.do";
    private static final String GET_MY_ATTENDANCE_LIST_URL = "/oaCustom/getMyAttendanceList.do";
    private static final String UPDATE_USER_INFO_URL = "/oaCustom/updateUserInfo.do";
    private static final String UPDATE_USER_HEAD_PHOTO_URL = "/oaCustom/updateUserHeadPhoto.do";

    public static String buildLoginUrl() {
        return new StringBuilder(BASE_URL).append(LOGIN_URL).toString();
    }

    public static String buildLaunchTansferUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_TANSFER_URL).toString();
    }

    public static String buildGetInFoodListUrl() {
        return new StringBuilder(BASE_URL).append(GET_IN_FOOD_LIST_URL).toString();
    }

    public static String buildGetConferenceUrl() {
        return new StringBuilder(BASE_URL).append(GET_CONFERENCE_URL).toString();
    }

    public static String buildGetInBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(GET_IN_BUY_ITEMS_URL).toString();
    }

    public static String buildGetLoginUserContactUrl() {
        return new StringBuilder(BASE_URL).append(GET_CONTACT_URL).toString();
    }

    public static String buildGetMyAttendanceListUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_ATTENDANCE_LIST_URL).toString();
    }

    public static String buildGetUserInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_USER_INFO_URL).toString();
    }

    public static String buildUpdateUserInfo() {
        return new StringBuilder(BASE_URL).append(UPDATE_USER_INFO_URL).toString();
    }
public static String buildUpdateUserHeadPhoto() {
        return new StringBuilder(BASE_URL).append(UPDATE_USER_HEAD_PHOTO_URL).toString();
    }

    public static List<String> parseImageUrl(String imagesUrl) {
        ArrayList<String> imageUrls = new ArrayList<>();
        if (imagesUrl != null) {
            String[] split = imagesUrl.split(IMAGE_STRING_SPLIT);
            for (String s : split) {
                imageUrls.add(new StringBuilder(BASE_URL).append(s).toString());
            }
        }
        return imageUrls;
    }

    public static String generateImageString(List<String> imagePaths) {
        if (imagePaths != null && imagePaths.size() != 0) {
            StringBuilder builder = new StringBuilder();
            for (String imagePath : imagePaths) {
                builder.append(imagePath).append(IMAGE_STRING_SPLIT);
            }
            return builder.delete(builder.length() - 1, builder.length()).toString();
        }
        return null;
    }
}
