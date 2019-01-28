package com.haokuo.wenyanoa.network;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.haokuo.wenyanoa.bean.SuccessBean;
import com.haokuo.wenyanoa.bean.UserInfoDetailBean;
import com.haokuo.wenyanoa.network.bean.AddDestinationParams;
import com.haokuo.wenyanoa.network.bean.ApprovalDetailParams;
import com.haokuo.wenyanoa.network.bean.BuyFoodInBasketParams;
import com.haokuo.wenyanoa.network.bean.ChatOnLineParams;
import com.haokuo.wenyanoa.network.bean.CodeCheckParams;
import com.haokuo.wenyanoa.network.bean.GetConferenceInfoParams;
import com.haokuo.wenyanoa.network.bean.GetDocInfoParams;
import com.haokuo.wenyanoa.network.bean.GetDocListParams;
import com.haokuo.wenyanoa.network.bean.GetInFoodListParams;
import com.haokuo.wenyanoa.network.bean.GetNewsInfoParams;
import com.haokuo.wenyanoa.network.bean.GetNoticeInfoParams;
import com.haokuo.wenyanoa.network.bean.GetResetVerfiyCodeParams;
import com.haokuo.wenyanoa.network.bean.GetStaffDestinationListParams;
import com.haokuo.wenyanoa.network.bean.HandlerApprovalParams;
import com.haokuo.wenyanoa.network.bean.LaunchApplyItemsParams;
import com.haokuo.wenyanoa.network.bean.LaunchChangeShiftParams;
import com.haokuo.wenyanoa.network.bean.LaunchLeaveParams;
import com.haokuo.wenyanoa.network.bean.LaunchRepairParams;
import com.haokuo.wenyanoa.network.bean.LaunchTripParams;
import com.haokuo.wenyanoa.network.bean.LoginParams;
import com.haokuo.wenyanoa.network.bean.ManageOrderParams;
import com.haokuo.wenyanoa.network.bean.ResetPasswordParams;
import com.haokuo.wenyanoa.network.bean.SaveBuyItemsParams;
import com.haokuo.wenyanoa.network.bean.SaveFoodInBasketParams;
import com.haokuo.wenyanoa.network.bean.UpdateAvatarParams;
import com.haokuo.wenyanoa.network.bean.UpdatePasswordParams;
import com.haokuo.wenyanoa.network.bean.base.IGetApiKey;
import com.haokuo.wenyanoa.network.bean.base.IGetParamsMap;
import com.haokuo.wenyanoa.network.bean.base.IdParams;
import com.haokuo.wenyanoa.network.bean.base.PageParamWithFillTime;
import com.haokuo.wenyanoa.network.bean.base.PageParams;
import com.haokuo.wenyanoa.network.bean.base.PageWithTimeParams;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zjf on 2018-07-21.
 */

public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static HttpHelper mInstance;
    private OkHttpClient mClient = new OkHttpClient();

    public static HttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }

    public HttpHelper() {
        mClient = new OkHttpClient();
    }

    static class OkHttpCallBack implements Callback {
        private NetworkCallback callback;
        private Handler mHandler;

        public OkHttpCallBack(NetworkCallback callback) {
            this.callback = callback;
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void onFailure(final Call call, final IOException e) {
            Log.e(TAG, "okhttp failed, message = " + e.getMessage());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String message;
                    if (e instanceof SocketTimeoutException) {
                        message = "连接超时";
                    } else if (e instanceof ConnectException) {
                        message = "连接异常";
                    } else {
                        message = e.getMessage();
                    }
                    callback.onFailure(call, message);
                }
            });
        }

        @Override
        public void onResponse(final Call call, Response response) {
            ResponseBody body = response.body();
            if (body == null) {
                Log.e(TAG, "okhttp response, ResponseBody = null.");
                callback.onFailure(call, "未知错误");
                return;
            }
            try {
                final String json = body.string();
                Log.v(TAG, "okhttp response, json = " + json);

                final SuccessBean successBean = JSON.parseObject(json, SuccessBean.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (successBean == null) {
                            callback.onFailure(call, "未知错误");
                        } else if (successBean.isSuccess()) {
                            callback.onSuccess(call, json);
                        } else {
                            String message = successBean.getMessage();
                            Log.e(TAG, "okhttp response, success = false, message = " + message);
                            callback.onFailure(call, message);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class OkDownloadCallBack implements Callback {
        private DownloadCallback callback;
        private Handler mHandler;

        public OkDownloadCallBack(DownloadCallback callback) {
            this.callback = callback;
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void onFailure(final Call call, final IOException e) {
            Log.e(TAG, "okhttp failed, message = " + e.getMessage());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String message;
                    if (e instanceof SocketTimeoutException) {
                        message = "连接超时";
                    } else if (e instanceof ConnectException) {
                        message = "连接异常";
                    } else {
                        message = e.getMessage();
                    }
                    callback.onFailure(call, message);
                }
            });
        }

        @Override
        public void onResponse(final Call call, Response response) {
            InputStream is = null;//输入流
            FileOutputStream fos = null;//输出流
            try {
                is = response.body().byteStream();//获取输入流
                final long total = response.body().contentLength();//获取文件大小
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onStart(call, total);
                    }
                });
                if (is != null) {
                    Log.d(TAG, "onResponse: 不为空");
                    File file = new File(callback.getFilePath());// 设置路径
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int ch = -1;
                    long process = 0;
                    while ((ch = is.read(buf)) != -1) {
                        fos.write(buf, 0, ch);
                        process += ch;
                        callback.onProgress(call, process);
                    }
                }
                fos.flush();
                // 下载完成
                if (fos != null) {
                    fos.close();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(call);
                    }
                });

            } catch (final Exception e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call, e.getMessage());
                    }
                });
                Log.d(TAG, e.toString());
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                }
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                }
            }
        }
    }
    //    private void doPost(IGetParamsMap iGetParamsMap, String url, NetworkCallback callback) {
    //        String jsonString = JSON.toJSONString(iGetParamsMap);
    //        Log.i(TAG, "doPost: " + "jsonString = " + jsonString);
    //        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
    //        MultipartBody.Builder builder = new MultipartBody.Builder()
    //                .setType(MultipartBody.FORM);
    //        if (iGetParamsMap.getParamsMap().entrySet().contains(UserIdApikeyParams.PARAM_APIKEY)) {
    //            FormBody.Builder builder2 = new FormBody.Builder();
    //            builder2.add(UserIdApikeyParams.PARAM_APIKEY, iGetParamsMap.getParamsMap().get(UserIdApikeyParams.PARAM_APIKEY));
    //            builder.addPart(builder2.build());
    //        }
    //        builder.addPart(requestBody);
    //        Request request = new Request.Builder()
    //                .post(requestBody)
    //                .url(url)
    //                .build();//创建Request 对象
    //        mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    //    }

    private void doPost(IGetApiKey iGetApiKey, String url, NetworkCallback callback) {
        String jsonString = JSON.toJSONString(iGetApiKey);
        url = new StringBuilder(url).append("?").append("apikey=").append(iGetApiKey.getApiKey()).toString();
        Log.i(TAG, "doPost: " + "jsonString = " + jsonString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();//创建Request 对象
        mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    }

    private void doPostWithoutApiKey(Object object, String url, NetworkCallback callback) {
        String jsonString = JSON.toJSONString(object);
        Log.i(TAG, "doPost: " + "jsonString = " + jsonString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();//创建Request 对象
        mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    }

    //
    //        private void doPostUploadFile(UploadFileParams uploadFileParams, String url, Object tag, NetworkCallback callback) {
    //            List<File> files = uploadFileParams.getFile();
    //            MultipartBody.Builder builder = new MultipartBody.Builder()
    //                    .setType(MultipartBody.FORM);
    //            for (File file : files) {
    //                RequestBody fileBody = RequestBody.create(MediaType.parse(uploadFileParams.getType()), file);
    //                builder.addFormDataPart("file", file.getName(), fileBody);
    //            }
    //            MultipartBody requestBody = builder.build();
    //            Request request = new Request.Builder()
    //                    .post(requestBody)
    //                    .url(url)
    //                    .tag(tag)
    //                    .build();//创建Request 对象
    //            mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    //        }

    public void cancelRequest(Object tag) {
        if (tag == null)
            return;
        for (Call call : mClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private void doPost(IGetParamsMap iGetParamsMap, String url, Object tag, NetworkCallback callback) {
        Request request = new Request.Builder()
                .post(buildFormBody(iGetParamsMap))
                .url(url)
                .tag(tag)
                .build();//创建Request 对象
        mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    }

    private void doGet(IGetParamsMap iGetParamsMap, String url, NetworkCallback callback) {
        url = buildFullUrl(url, iGetParamsMap);
        Log.v(TAG, url);
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();//创建Request 对象
        mClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    }

    @NonNull
    private FormBody buildFormBody(IGetParamsMap iGetParamsMap) {
        FormBody.Builder builder = new FormBody.Builder();
        Map<String, String> params = iGetParamsMap.getParamsMap();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                builder.add(entry.getKey(), value);
            }
        }
        return builder.build();
    }

    private String buildFullUrl(String url, IGetParamsMap iGetParamsMap) {
        if(iGetParamsMap==null) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        Map<String, String> params = iGetParamsMap.getParamsMap();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                builder.append(entry.getKey()).append("=").append(value).append("&");
            }
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    /** 登录 */
    public void login(LoginParams params, NetworkCallback callback) {
        doPostWithoutApiKey(params, UrlBuilder.buildLoginUrl(), callback);
    }

    /** 获取菜品列表 */
    public void getFoodList(GetInFoodListParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetInFoodListUrl(), callback);
    }

    /** 保存菜品到我的菜篮 */
    public void saveFoodInBasket(SaveFoodInBasketParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildSaveFoodInBasketUrl(), callback);
    }

    /** 获取我的菜篮列表 */
    public void getBasketList(PageParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetBasketListUrl(), callback);
    }

    /** 获取我的订单 */
    public void getFoodOrderList(PageWithTimeParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetFoodOrderListUrl(), callback);
    }

    /** 管理订单数据 */
    public void manageOrderList(ManageOrderParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildManageOrderListUrl(), callback);
    }

    /** 购买菜篮中的菜品 */
    public void buyFoodInBasket(BuyFoodInBasketParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildBuyFoodInBasketUrl(), callback);
    }

    /** 购买菜篮中的菜品 */
    public void deleteItemById(IdParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildDeleteItemByIdUrl(), callback);
    }

    /** 发起物品申购前的数据准备 */
    public void getInBuyItems(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetInBuyItemsUrl(), callback);
    }

    /** 获取联系人列表 */
    public void getContactList(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetLoginUserContactUrl(), callback);
    }

    /** 获取我的出勤列表 */
    public void getMyAttendanceList(PageWithTimeParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyAttendanceListUrl(), callback);
    }

    /** 获取用户信息 */
    public void getUserInfoUrl(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUserInfoUrl(), callback);
    }

    /** 修改用户信息 */
    public void updateUserInfo(UserInfoDetailBean params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildUpdateUserInfoUrl(), callback);
    }

    /** 修改用户头像 */
    public void updateUserHeadPhoto(UpdateAvatarParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildUpdateUserHeadPhotoUrl(), callback);
    }

    /** 修改密码 */
    public void updatePassword(UpdatePasswordParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildUpdatePasswordUrl(), callback);
    }

    /** 获取验证码 */
    public void getResetVerfiyCode(GetResetVerfiyCodeParams params, NetworkCallback callback) {
        String url = UrlBuilder.buildGetResetVerfiyCodeUrl()+"?telphone="+params.getTelphone();
        doPostWithoutApiKey(params, url, callback);
    }

    /** 短信验证码校验 */
    public void codeCheck(CodeCheckParams params, NetworkCallback callback) {
        String url = UrlBuilder.buildCodeCheckUrl()+"?telphone="+params.getTelphone()+"&authCode="+params.getAuthCode();
        doPostWithoutApiKey(params, url, callback);
    }

    /** 重置密码 */
    public void resetPassword(ResetPasswordParams params, NetworkCallback callback) {
        String url = UrlBuilder.buildResetPasswordUrl()+"?telphone="+params.getTelphone()+"&authCode="+params.getAuthCode()+"&password="+params.getPassword();
        doPostWithoutApiKey(params, url, callback);
    }

    /** 获取会议通知 */
    public void getConference(PageParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetConferenceUrl(), callback);
    }

    /** 获取会议通知详情 */
    public void getConferenceInfo(GetConferenceInfoParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetConferenceInfoUrl(), callback);
    }

    /** 获取行业新闻 */
    public void getNewsList(PageParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetNewsListUrl(), callback);
    }

    /** 获取行业新闻详情 */
    public void getNewsInfo(GetNewsInfoParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetNewsInfoUrl(), callback);
    }

    /** 获取通知公告 */
    public void getNoticeList(PageParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetNoticeListUrl(), callback);
    }

    /** 获取通知公告详情 */
    public void getNoticeInfo(GetNoticeInfoParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetNoticeInfoUrl(), callback);
    }

    /** 我的钱包 */
    public void getMyWallet(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyWalletUrl(), callback);
    }

    /** 发起物品申购 */
    public void saveBuyItems(SaveBuyItemsParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildSaveBuyItemsUrl(), callback);
    }

    /** 发起物品保修前的数据准备 */
    public void prepareRepair(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildPrepareRepairUrl(), callback);
    }

    /** 发起物品保修 */
    public void launchRepair(LaunchRepairParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchRepairUrl(), callback);
    }

    /** 发起物品领用前的数据准备 */
    public void prepareApplyItems(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildPrepareApplyItemsUrl(), callback);
    }

    /** 发起物品领用 */
    public void launchApplyItems(LaunchApplyItemsParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchApplyItemsUrl(), callback);
    }

    /** 发起公差前的数据准备 */
    public void prepareTrip(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildPrepareTripUrl(), callback);
    }

    /** 发起公差 */
    public void launchTrip(LaunchTripParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchTripUrl(), callback);
    }

    /** 发起事假前的数据准备 */
    public void prepareLeave(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildPrepareLeaveUrl(), callback);
    }

    /** 发起事假 */
    public void launchLeave(LaunchLeaveParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchLeaveUrl(), callback);
    }

    /** 发起调班前的数据准备 */
    public void prepareChangeShift(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildPrepareChangeShiftUrl(), callback);
    }

    /** 发起调班 */
    public void launchChangeShift(LaunchChangeShiftParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchChangeShiftUrl(), callback);
    }

    /** 获取我的待审批事假 */
    public void getUnapprovedLeave(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedLeaveUrl(), callback);
    }

    /** 我发起的事假 */
    public void getMyLeave(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyLeaveUrl(), callback);
    }

    /** 获取我的待审批公差 */
    public void getUnapprovedTrip(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedTripUrl(), callback);
    }

    /** 我发起的公差 */
    public void getMyTrip(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyTripUrl(), callback);
    }

    /** 获取我的待审批调班 */
    public void getUnapprovedChangeShift(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedChangeShiftUrl(), callback);
    }

    /** 我发起的调班 */
    public void getMyChangeShift(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyChangeShiftUrl(), callback);
    }

    /** 获取我的待审批申购 */
    public void getUnapprovedBuyItems(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedBuyItemsUrl(), callback);
    }

    /** 我发起的申购 */
    public void getMyBuyItems(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyBuyItemsUrl(), callback);
    }

    /** 获取我的待审批报修 */
    public void getUnapprovedRepair(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedRepairUrl(), callback);
    }

    /** 我发起的报修 */
    public void getMyRepair(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyRepairUrl(), callback);
    }

    /** 获取我的待审批领用 */
    public void getUnapprovedApplyItems(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUnapprovedApplyItemsUrl(), callback);
    }

    /** 我发起的领用 */
    public void getMyApplyItems(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetMyApplyItemsUrl(), callback);
    }

    /** 根据ID获取事假详情 */
    public void getLeaveById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetLeaveByIdUrl(), callback);
    }

    /** 同意事假申请 */
    public void agreeLeave(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeLeaveUrl(), callback);
    }

    /** 拒绝事假申请 */
    public void rejectLeave(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectLeaveUrl(), callback);
    }

    /** 根据ID获取公差详情 */
    public void getTripById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetTripByIdUrl(), callback);
    }

    /** 同意公差申请 */
    public void agreeTrip(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeTripUrl(), callback);
    }

    /** 拒绝公差申请 */
    public void rejectTrip(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectTripUrl(), callback);
    }

    /** 根据ID获取调班详情 */
    public void getChangeShiftById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetChangeShiftByIdUrl(), callback);
    }

    /** 同意调班申请 */
    public void agreeChangeShift(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeChangeShiftUrl(), callback);
    }

    /** 拒绝调班申请 */
    public void rejectChangeShift(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectChangeShiftUrl(), callback);
    }

    /** 根据ID获取申购详情 */
    public void getBuyItemsById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetBuyItemsByIdUrl(), callback);
    }

    /** 同意申购申请 */
    public void agreeBuyItems(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeBuyItemsUrl(), callback);
    }

    /** 拒绝申购申请 */
    public void rejectBuyItems(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectBuyItemsUrl(), callback);
    }

    /** 根据ID获取报修详情 */
    public void getRepairById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetRepairByIdUrl(), callback);
    }

    /** 同意报修申请 */
    public void agreeRepair(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeRepairUrl(), callback);
    }

    /** 拒绝报修申请 */
    public void rejectRepair(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectRepairUrl(), callback);
    }

    /** 根据ID获取领用详情 */
    public void getApplyItemsById(ApprovalDetailParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetApplyItemsByIdUrl(), callback);
    }

    /** 同意领用申请 */
    public void agreeApplyItems(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAgreeApplyItemsUrl(), callback);
    }

    /** 拒绝领用申请 */
    public void rejectApplyItems(HandlerApprovalParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildRejectApplyItemsUrl(), callback);
    }

    /** 获取抄送给我信息 */
    public void getCopy2MeApproval(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildCopy2MeApprovalUrl(), callback);
    }

    /** 获取人员去向列表 */
    public void getStaffDestinationList(GetStaffDestinationListParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetStaffDestinationListUrl(), callback);
    }

    /** 根据ID获取人员去向列表详情 */
    public void getStaffDestinationInfo(PageParamWithFillTime params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetStaffDestinationInfoUrl(), callback);
    }

    /** 删除人员去向 */
    public void deleteDestination(IdParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildDeleteDestinationUrl(), callback);
    }

    /** 新增人员去向 */
    public void addDestination(AddDestinationParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildAddDestinationUrl(), callback);
    }

    /** 修改人员去向 */
    public void editDestination(AddDestinationParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildEditDestinationUrl(), callback);
    }

    /** 获取公文列表 */
    public void getDocList(GetDocListParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetDocListUrl(), callback);
    }

    /** 获取公文详情 */
    public void getDocInfo(GetDocInfoParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetDocInfoUrl(), callback);
    }

    /** 获取用户权限ID */
    public void getUserPermission(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetUserPermissionUrl(), callback);
    }

    /** 获取我的出勤天数和和待审批申请数量 */
    public void getAppSomeCount(UserIdApiKeyParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetAppSomeCountUrl(), callback);
    }

    /** 获取我的出勤天数和和待审批申请数量 */
    public void chatOnLine(ChatOnLineParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildChatOnLineUrl(), callback);
    }
    /** 获取我的出勤天数和和待审批申请数量 */
    public void getNewVersion(NetworkCallback callback) {
        doGet(null, UrlBuilder.buildGetNewVersionUrl(), callback);
    }
    /** 下载文件 */
    public void downloadFile(String url, DownloadCallback callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new OkDownloadCallBack(callBack));
    }
}
