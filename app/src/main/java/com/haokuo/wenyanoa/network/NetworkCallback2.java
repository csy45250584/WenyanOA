package com.haokuo.wenyanoa.network;

/**
 * Created by zjf on 2018-07-21.
 */

public interface NetworkCallback2 {

    void onSuccess(String json);

    void onFailure(String message);
}