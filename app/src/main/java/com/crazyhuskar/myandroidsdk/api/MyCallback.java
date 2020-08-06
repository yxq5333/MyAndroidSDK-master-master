package com.crazyhuskar.myandroidsdk.api;

/**
 * 请求接听类
 * @author CrazyHuskar
 * @date 2018/6/12
 */

public interface MyCallback<T> {
    void onSuccess(T data);

    void onFailure(String msg);

    void onError();

    void onComplete();
}
