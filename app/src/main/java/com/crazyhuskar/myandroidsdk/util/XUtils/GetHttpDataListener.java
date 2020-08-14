package com.crazyhuskar.myandroidsdk.util.XUtils;

import com.crazyhuskar.myandroidsdk.util.bean.ResponseInfo;

import org.xutils.common.Callback;

/**
 * 获取网络数据监听
 * Created by HG on 2018-01-17.
 */

public interface GetHttpDataListener {

    void onGetSuccess(String result, ResponseInfo responseInfo);

    void onGetError(Throwable ex);

    void onGetLoading(long total, long current);

    void onGetFinish();

    void onGetCancel(Callback.CancelledException cex);

}
