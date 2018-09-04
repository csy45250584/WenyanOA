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
    private static final String BUY_FOOD_IN_BASKET = "/mess/saveInOrder.do";
    private static final String GET_BASKET_LIST_URL = "/mess/getIPopuralityList.do";
    private static final String DELETE_BASKET_ITEM_BY_ID = "/mess/delInPreOrderById.do";
    //物品申购
    private static final String GET_IN_BUY_ITEMS_URL = "/oaOffice/getInbuyitems.do";
    private static final String SAVE_BUY_ITEMS_URL = "/oaOffice/saveInBuyItems.do";
    private static final String GET_UNAPPROVED_BUY_ITEMS_URL = "/oaOffice/doDealWithBuyItem.do";
    private static final String GET_MY_BUY_ITEMS_URL = "/oaOffice/getMylistBuy.do";
    private static final String GET_BUY_ITEMS_BY_ID_URL = "/oaOffice/getInfoBuy.do";
    private static final String AGREE_BUY_ITEMS_URL = "/oaOffice/updatebuyItem.do";
    private static final String REJECT_BUY_ITEMS_URL = "/oaOffice/buyInReusefor.do";
    //物品报修
    private static final String PREPARE_REPAIR_URL = "/oaOffice/getInfix.do";
    private static final String LAUNCH_REPAIR_URL = "/oaOffice/launchFix.do";
    private static final String GET_UNAPPROVED_REPAIR_URL = "/oaOffice/doDealWithFix.do";
    private static final String GET_MY_REPAIR_URL = "/oaOffice/getInlistFix.do";
    private static final String GET_REPAIR_BY_ID_URL = "/oaOffice/getInfofix.do";
    private static final String AGREE_REPAIR_URL = "/oaOffice/agreeFixState.do";
    private static final String REJECT_REPAIR_URL = "/oaOffice/infixReusefor.do";
    //物品领用
    private static final String PREPARE_APPLY_ITEMS_URL = "/oaOffice/getInItemsapply.do";
    private static final String LAUNCH_APPLY_ITEMS_URL = "/oaOffice/launchItemsApply.do";
    private static final String GET_UNAPPROVED_APPLY_ITEMS_URL = "/oaOffice/doDealWithTransfer.do";
    private static final String GET_MY_APPLY_ITEMS_URL = "/oaOffice/loginItemsapply.do";
    private static final String GET_APPLY_ITEMS_BY_ID_URL = "/oaOffice/getInfoItemsapply.do";
    private static final String AGREE_APPLY_ITEMS_URL = "/oaOffice/updateItemsApply.do";
    private static final String REJECT_APPLY_ITEMS_URL = "/oaOffice/refuseItemsApply.do";
    //用户公差
    private static final String PREPARE_TRIP_URL = "/oaOffice/getInTrip.do";
    private static final String LAUNCH_TRIP_URL = "/oaOffice/launchTrip.do";
    private static final String GET_UNAPPROVED_TRIP_URL = "/oaOffice/getaMyDealtWithTripList.do";
    private static final String GET_MY_TRIP_URL = "/oaOffice/getInlistTrip.do";
    private static final String GET_TRIP_BY_ID_URL = "/oaOffice/getInfoTrip.do";
    private static final String AGREE_TRIP_URL = "/oaOffice/toDealtWithTrip.do";
    private static final String REJECT_TRIP_URL = "/oaOffice/toDealtRefusedTrip.do";

    //用户事假
    private static final String PREPARE_LEAVE_URL = "/oaOffice/getInApprove.do";
    private static final String LAUNCH_LEAVE_URL = "/oaOffice/launchApproce.do";
    private static final String GET_UNAPPROVED_LEAVE_URL = "/oaOffice/getUserDealtWithapproveList.do";
    private static final String GET_MY_LEAVE_URL = "/oaOffice/getInlistApprove.do";
    private static final String GET_LEAVE_BY_ID_URL = "/oaOffice/getApproveInfoById.do";
    private static final String AGREE_LEAVE_URL = "/oaOffice/toDealtWithApprove.do";
    private static final String REJECT_LEAVE_URL = "/oaOffice/toDealtRefusedApprove.do";
    //用户调班
    private static final String PREPARE_CHANGE_SHIFT_URL = "/oaCustom/gotoInTransfer.do";
    private static final String LAUNCH_CHANGE_SHIFT_URL = "/oaCustom/launchTansfer.do";
    private static final String GET_UNAPPROVED_CHANGE_SHIFT_URL = "/oaCustom/doDealWithTransfer.do";
    private static final String GET_MY_CHANGE_SHIFT_URL = "/oaCustom/loginUserTransfer.do";
    private static final String GET_CHANGE_SHIFT_BY_ID_URL = "/oaCustom/getInfoTransfer.do";
    private static final String AGREE_CHANGE_SHIFT_URL = "/oaCustom/updateTransferA.do";
    private static final String REJECT_CHANGE_SHIFT_URL = "/oaCustom/updateTransferN.do";
    //工作信息
    private static final String GET_STAFF_DESTINATION_LIST_URL = "/noteNew/getLoginUserContact.do";
    private static final String GET_STAFF_DESTINATION_INFO_URL = "/noteNew/getInfoUserLocal.do";
    private static final String DELETE_DESTINATION_URL = "/noteNew/delInUserLocal.do";
    private static final String ADD_DESTINATION_URL = "/noteNew/addUserLocal.do";
    private static final String EDIT_DESTINATION_URL = "/noteNew/editUserLocal.do";
    //公文管理
    private static final String GET_DOC_LIST_URL = "/oaOffice/getInBumfListJson.do";
    private static final String GET_DOC_INFO_URL = "/oaOffice/toGetInfoSendBumf.do";

    public static String buildGetStaffDestinationInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_STAFF_DESTINATION_INFO_URL).toString();
    }

    public static String buildGetDocListUrl() {
        return new StringBuilder(BASE_URL).append(GET_DOC_LIST_URL).toString();
    }

    public static String buildGetDocInfoUrl() {
        return new StringBuilder(BASE_URL).append(GET_DOC_INFO_URL).toString();
    }

    public static String buildAddDestinationUrl() {
        return new StringBuilder(BASE_URL).append(ADD_DESTINATION_URL).toString();
    }

    public static String buildEditDestinationUrl() {
        return new StringBuilder(BASE_URL).append(EDIT_DESTINATION_URL).toString();
    }

    public static String buildDeleteDestinationUrl() {
        return new StringBuilder(BASE_URL).append(DELETE_DESTINATION_URL).toString();
    }

    public static String buildGetStaffDestinationListUrl() {
        return new StringBuilder(BASE_URL).append(GET_STAFF_DESTINATION_LIST_URL).toString();
    }

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

    public static String buildBuyFoodInBasketUrl() {
        return new StringBuilder(BASE_URL).append(BUY_FOOD_IN_BASKET).toString();
    }

    public static String buildGetBasketListUrl() {
        return new StringBuilder(BASE_URL).append(GET_BASKET_LIST_URL).toString();
    }

    public static String buildGetLeaveByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_LEAVE_BY_ID_URL).toString();
    }

    public static String buildGetTripByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_TRIP_BY_ID_URL).toString();
    }

    public static String buildGetChangeShiftByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_CHANGE_SHIFT_BY_ID_URL).toString();
    }

    public static String buildGetBuyItemsByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_BUY_ITEMS_BY_ID_URL).toString();
    }

    public static String buildGetRepairByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_REPAIR_BY_ID_URL).toString();
    }

    public static String buildGetApplyItemsByIdUrl() {
        return new StringBuilder(BASE_URL).append(GET_APPLY_ITEMS_BY_ID_URL).toString();
    }

    public static String buildAgreeLeaveUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_LEAVE_URL).toString();
    }

    public static String buildRejectLeaveUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_LEAVE_URL).toString();
    }

    public static String buildAgreeTripUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_TRIP_URL).toString();
    }

    public static String buildRejectTripUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_TRIP_URL).toString();
    }

    public static String buildAgreeChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_CHANGE_SHIFT_URL).toString();
    }

    public static String buildRejectChangeShiftUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_CHANGE_SHIFT_URL).toString();
    }

    public static String buildAgreeBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_BUY_ITEMS_URL).toString();
    }

    public static String buildRejectBuyItemsUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_BUY_ITEMS_URL).toString();
    }

    public static String buildAgreeRepairUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_REPAIR_URL).toString();
    }

    public static String buildRejectRepairUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_REPAIR_URL).toString();
    }

    public static String buildAgreeApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(AGREE_APPLY_ITEMS_URL).toString();
    }

    public static String buildRejectApplyItemsUrl() {
        return new StringBuilder(BASE_URL).append(REJECT_APPLY_ITEMS_URL).toString();
    }

    public static String buildDeleteItemByIdUrl() {
        return new StringBuilder(BASE_URL).append(DELETE_BASKET_ITEM_BY_ID).toString();
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
