package com.crazyhuskar.myandroidsdk.util.XUtils;

import org.xutils.common.Callback;

/**
 * 上传文件监听
 * Created by HG on 2018-01-17.
 */

public interface UploadListener {

    void onUploadSuccess(String result);

    void onUploadError(Throwable ex);

    void onUploadLoading(long total, long current);

    void onUploadFinish();

    void onUploadCancel(Callback.CancelledException cex);

}
