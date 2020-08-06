package com.crazyhuskar.myandroidsdk.api;

/**
 * 请求接听类
 * @author CrazyHuskar
 * @date 2018/6/12
 */

public interface MyProgressCallback<T> {
    void onSuccess(T object);

    void onFailure(String msg);

    void onError();

    void onComplete();

    /**
     * @param progress
     * @param total
     * @param done
     */
    void onProgress(long progress, long total, boolean done);
}
