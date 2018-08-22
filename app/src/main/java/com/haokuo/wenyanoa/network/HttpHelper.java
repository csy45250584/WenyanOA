package com.haokuo.wenyanoa.network;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.haokuo.wenyanoa.bean.SuccessBean;
import com.haokuo.wenyanoa.bean.UserInfoDetailBean;
import com.haokuo.wenyanoa.network.bean.GetInFoodListParams;
import com.haokuo.wenyanoa.network.bean.LaunchTansferParams;
import com.haokuo.wenyanoa.network.bean.LoginParams;
import com.haokuo.wenyanoa.network.bean.UpdateAvatarParams;
import com.haokuo.wenyanoa.network.bean.base.IGetApiKey;
import com.haokuo.wenyanoa.network.bean.base.IGetParamsMap;
import com.haokuo.wenyanoa.network.bean.base.PageWithTimeParams;
import com.haokuo.wenyanoa.network.bean.base.UserIdApiKeyParams;

import java.io.IOException;
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

    public void launchTansfer(LaunchTansferParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildLaunchTansferUrl(), callback);
    }

    public void getInFoodList(GetInFoodListParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildGetInFoodListUrl(), callback);
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
        doPost(params, UrlBuilder.buildUpdateUserInfo(), callback);
    }

    /** 修改用户头像 */
    public void updateUserHeadPhoto(UpdateAvatarParams params, NetworkCallback callback) {
        doPost(params, UrlBuilder.buildUpdateUserHeadPhoto(), callback);
    }
}
