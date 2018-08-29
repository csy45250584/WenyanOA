package com.haokuo.wenyanoa.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjf on 2018-07-21.
 */

public class UrlBuilder {
    private static final String IMAGE_STRING_SPLIT = ",";
    private static final String BASE_URL = "http://192.168.1.92:9079/oai/";
    //用户登录
    private static final String LOGIN_URL = "/oaCustom/loginByPassword.do";
    private static final String UPDATE_PASSWORD_URL = "/oaCustom/updatePassword.do";
    private static final String GET_USER_INFO_URL = "/oaCustom/getUserInfo.do";
    private static final String UPDATE_USER_INFO_URL = "/oaCustom/updateUserInfo.do";
    private static final String UPDATE_USER_HEAD_PHOTO_URL = "/oaCustom/updateUserHeadPhoto.do";
    private static final String GET_RESET_VERFIY_CODE_URL = "/oamessage/getResetVerfiyCode.do";
    private static final String CODE_CHECK_URL = "/oamessage/codeCheck.do";
    private static final String FORGET_PASSWORD_URL = "/oamessage/forgetPassword.do";
    //用户相关信息
    private static final String GET_CONTACT_URL = "/oaCustom/getLoginUserContact.do";
    private static final String GET_MY_ATTENDANCE_LIST_URL = "/oaCustom/getMyAttendanceList.do";
    private static final String GET_MY_WALLET_URL = "/oaCustom/listMywallet.do";
    private static final String GET_COPY2ME_APPROVAL_URL = "/oaOffice/getInUnmessageList.do";
    //通知讯息类
    private static final String GET_CONFERENCE_URL = "/noteNew/getInConferenceJson.do";
    private static final String GET_INFO_CONFERENCE_URL = "/noteNew/getInfoConference.do";
    private static final String GET_NEWS_LIST_URL = "/noteNew/getInNewsList.do";
    private static final String GET_NEWS_INFO_URL = "/noteNew/getInfoNews.do";
    private static final String GET_NOTICE_LIST_URL = "/noteNew/getInNewsSortList.do";
    private static final String GET_NOTICE_INFO_URL = "/noteNew/getInfoNewsSort.do";
    //食堂部分接口
    private static final String GET_IN_FOOD_LIST_URL = "/mess/getInFoodList.do";
    private static final String GET_FOOD_ORDER_LIST_URL = "/mess/getInOrderList.do";
    private static final String SAVE_FOOD_IN_BASKET_URL = "/mess/saveInPreOrder.do";
    private static final String GET_BASKET_LIST_URL = "/mess/getIPopuralityList.do";
    //物品申购
    private static final String GET_IN_BUY_ITEMS_URL = "/oaOffice/getInbuyitems.do";
    private static final String SAVE_BUY_ITEMS_URL = "/oaOffice/saveInBuyItems.do";
    private static final String GET_UNAPPROVED_BUY_ITEMS_URL = "/oaOffice/doDealWithBuyItem.do";
    private static final String GET_MY_BUY_ITEMS_URL = "/oaOffice/getMylistBuy.do";
    //物品报修
    private static final String PREPARE_REPAIR_URL = "/oaOffice/getInfix.do";
    private static final String LAUNCH_REPAIR_URL = "/oaOffice/launchFix.do";
    private static final String GET_UNAPPROVED_REPAIR_URL = "/oaOffice/doDealWithFix.do";
    private static final String GET_MY_REPAIR_URL = "/oaOffice/getInlistFix.do";
    //物品领用
    private static final String PREPARE_APPLY_ITEMS_URL = "/oaOffice/getInItemsapply.do";
    private static final String LAUNCH_APPLY_ITEMS_URL = "/oaOffice/launchItemsApply.do";
    private static final String GET_UNAPPROVED_APPLY_ITEMS_URL = "/oaOffice/doDealWithTransfer.do";
    private static final String GET_MY_APPLY_ITEMS_URL = "/oaOffice/loginItemsapply.do";
    //用户公差
    private static final String PREPARE_TRIP_URL = "/oaOffice/getInTrip.do";
    private static final String LAUNCH_TRIP_URL = "/oaOffice/launchTrip.do";
    private static final String GET_UNAPPROVED_TRIP_URL = "/oaOffice/getaMyDealtWithTripList.do";
    private static final String GET_MY_TRIP_URL = "/oaOffice/getInlistTrip.do";

    //用户事假
    private static final String PREPARE_LEAVE_URL = "/oaOffice/getInApprove.do";
    private static final String LAUNCH_LEAVE_URL = "/oaOffice/launchApproce.do";
    private static final String GET_UNAPPROVED_LEAVE_URL = "/oaOffice/getUserDealtWithapproveList.do";
    private static final String GET_MY_LEAVE_URL = "/oaOffice/getInlistApprove.do";
    //用户调班
    private static final String PREPARE_CHANGE_SHIFT_URL = "/oaCustom/gotoInTransfer.do";
    private static final String LAUNCH_CHANGE_SHIFT_URL = "/oaCustom/launchTansfer.do";
    private static final String GET_UNAPPROVED_CHANGE_SHIFT_URL = "/oaCustom/doDealWithTransfer.do";
    private static final String GET_MY_CHANGE_SHIFT_URL = "/oaCustom/loginUserTransfer.do";

    public static String buildLoginUrl() {
        return new StringBuilder(BASE_URL).append(LOGIN_URL).toString();
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

    public static String buildUpdateUserInfoUrl() {
        return new StringBuilder(BASE_URL).append(UPDATE_USER_INFO_URL).toString();
    }

    public static String buildUpdateUserHeadPhotoUrl() {
        return new StringBuilder(BASE_URL).append(UPDATE_USER_HEAD_PHOTO_URL).toString();
    }

    public static String buildUpdatePasswordUrl() {
        return new StringBuilder(BASE_URL).append(UPDATE_PASSWORD_URL).toString();
    }

    public static String buildGetResetVerfiyCodeUrl() {
        return new StringBuilder(BASE_URL).append(GET_RESET_VERFIY_CODE_URL).toString();
    }

    public static String buildCodeCheckUrl() {
        return new StringBuilder(BASE_URL).append(CODE_CHECK_URL).toString();
    }

    public static String buildResetPasswordUrl() {
        return new StringBuilder(BASE_URL).append(FORGET_PASSWORD_URL).toString();
    }

    public static String buildGetNewsListUrl() {
        return new StringBuilder(BASE_URL).append(GET_NEWS_LIST_URL).toString();
    }

    public static String buildGetNoticeListUrl() {
        return new StringBuilder(BASE_URL).append(GET_NOTICE_LIST_URL).toString();
    }

    public static String buildGetConferenceInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_INFO_CONFERENCE_URL).toString();
    }

    public static String buildGetNewsInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_NEWS_INFO_URL).toString();
    }

    public static String buildGetNoticeInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_NOTICE_INFO_URL).toString();
    }

    public static String buildGetMyWalletUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_WALLET_URL).toString();
    }

    public static String buildGetFoodOrderListUrl() {
        return new StringBuilder(BASE_URL).append(GET_FOOD_ORDER_LIST_URL).toString();
    }

    public static String buildSaveBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(SAVE_BUY_ITEMS_URL).toString();
    }

    public static String buildPrepareRepairUrl() {
        return new StringBuilder(BASE_URL).append(PREPARE_REPAIR_URL).toString();
    }

    public static String buildLaunchRepairUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_REPAIR_URL).toString();
    }

    public static String buildPrepareApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(PREPARE_APPLY_ITEMS_URL).toString();
    }

    public static String buildLaunchApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_APPLY_ITEMS_URL).toString();
    }

    public static String buildPrepareTripUrl() {
        return new StringBuilder(BASE_URL).append(PREPARE_TRIP_URL).toString();
    }

    public static String buildLaunchTripUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_TRIP_URL).toString();
    }

    public static String buildPrepareLeaveUrl() {
        return new StringBuilder(BASE_URL).append(PREPARE_LEAVE_URL).toString();
    }

    public static String buildLaunchLeaveUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_LEAVE_URL).toString();
    }

    public static String buildPrepareChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(PREPARE_CHANGE_SHIFT_URL).toString();
    }

    public static String buildLaunchChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(LAUNCH_CHANGE_SHIFT_URL).toString();
    }

    public static String buildGetUnapprovedLeaveUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_LEAVE_URL).toString();
    }

    public static String buildGetMyLeaveUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_LEAVE_URL).toString();
    }

    public static String buildGetUnapprovedTripUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_TRIP_URL).toString();
    }

    public static String buildGetMyTripUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_TRIP_URL).toString();
    }

    public static String buildGetUnapprovedChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_CHANGE_SHIFT_URL).toString();
    }

    public static String buildGetMyChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_CHANGE_SHIFT_URL).toString();
    }

    public static String buildGetUnapprovedBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_BUY_ITEMS_URL).toString();
    }

    public static String buildGetMyBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_BUY_ITEMS_URL).toString();
    }

    public static String buildGetUnapprovedRepairUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_REPAIR_URL).toString();
    }

    public static String buildGetMyRepairUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_REPAIR_URL).toString();
    }

    public static String buildGetUnapprovedApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(GET_UNAPPROVED_APPLY_ITEMS_URL).toString();
    }

    public static String buildGetMyApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(GET_MY_APPLY_ITEMS_URL).toString();
    }

    public static String buildCopy2MeApprovalUrl() {
        return new StringBuilder(BASE_URL).append(GET_COPY2ME_APPROVAL_URL).toString();
    }

    public static String buildSaveFoodInBasketUrl() {
        return new StringBuilder(BASE_URL).append(SAVE_FOOD_IN_BASKET_URL).toString();
    }

    public static String buildGetBasketListUrl() {
        return new StringBuilder(BASE_URL).append(GET_BASKET_LIST_URL).toString();
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
