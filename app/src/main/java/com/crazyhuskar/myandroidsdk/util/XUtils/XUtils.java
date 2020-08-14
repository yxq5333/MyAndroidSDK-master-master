package com.crazyhuskar.myandroidsdk.util.XUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.SystemConfig;
import com.crazyhuskar.myandroidsdk.base.MyBaseApplication;
import com.crazyhuskar.myandroidsdk.util.MyUtilEncrypt;
import com.crazyhuskar.myandroidsdk.util.MyUtilAppInfo;
import com.crazyhuskar.myandroidsdk.util.bean.KeyValue;
import com.crazyhuskar.myandroidsdk.util.bean.ResponseInfo;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import org.xutils.ex.DbException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Constants;

/**
 * XUtils工具类
 * Created by HG
 */
public class XUtils {

    /**
     * 获取图片监听
     **/
    private LoadImageListener loadImageListener = null;
    /**
     * 获取网络数据监听
     **/
    private GetHttpDataListener getHttpDataListener = null;
    /**
     * 下载文件监听
     **/
    private DownloadListener downloadListener = null;
    /**
     * 上传文件监听
     **/
    private UploadListener uploadListener = null;
    /**
     * 文件上传默认的key
     */
    private final String DEFAULT_UPLOAD_FILE_KEY = "file";
    private static Context context;

    /*************************************** 暴露的方法 ***************************************/

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(Application application) {

        context = application.getApplicationContext();
        // 初始化
        x.Ext.init(application);
        // 是否输出debug日志, 开启debug会影响性能.
        x.Ext.setDebug(MyUtilAppInfo.isDebug(MyBaseApplication.getInstance().getApplicationContext()));
        // 创建数据库
        try {
            XDBUtils.getDbManager();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片
     *
     * @param loadImageOptions 获取图片配置
     */
    public void loadImageByUrl(LoadImageOptions loadImageOptions) {

        ImageOptions imageOptions = new ImageOptions.Builder()//
                .setSize(DensityUtil.dip2px(loadImageOptions.widthDp),
                        DensityUtil.dip2px(loadImageOptions.heightDp))// 图片大小
                .setRadius(DensityUtil.dip2px(loadImageOptions.radiusDp))// ImageView圆角半径
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(loadImageOptions.scaleType)//
                .setLoadingDrawableId(loadImageOptions.loadIconRes)// 加载中默认显示图片
                .setFailureDrawableId(loadImageOptions.failIconRes)// 加载失败后默认显示图片
                .setFadeIn(loadImageOptions.isFadeIn)// 加载图片完成后，显示图片时是否渐变显示
                .setCircular(loadImageOptions.isCircular)// 是否显示圆形图片
                .build();

        // TODO 缓存地址
        String cachePath = SystemConfig.PHOTO_CACHE_DIR;
        // 缓存名
        String cacheName = MyUtilEncrypt.md5Encrypt(loadImageOptions.url + loadImageOptions.widthDp + loadImageOptions.heightDp);
        // 是否从网络加载
        boolean isFromNet = true;

        // 判断图片是否有缓存
        // 有缓存则从本地加载
        // 否则从网络加载
        File file = new File(cachePath, cacheName);
        if (file.exists()) {
            loadImageOptions.url = cachePath + cacheName;
            isFromNet = false;
        }

        if (!loadImageOptions.url.startsWith("http:")) {
            isFromNet = false;
        }

        boolean isPng;
        if (loadImageOptions.url.contains("png") || loadImageOptions.url.contains("PNG")) {
            isPng = true;
        } else {
            isPng = false;
        }

        // 判断要加载的控件是否为ImageView
        if (loadImageOptions.view == null || !(loadImageOptions.view instanceof ImageView)) {
            x.image().loadDrawable(loadImageOptions.url, imageOptions,
                    new LoadImageCallBack(loadImageOptions.url, isFromNet, cachePath, cacheName, loadImageOptions.view, isPng));
        } else {
            x.image().bind((ImageView) (loadImageOptions.view), loadImageOptions.url, imageOptions,
                    new LoadImageCallBack(loadImageOptions.url, isFromNet, cachePath, cacheName, loadImageOptions.view, isPng));
        }
    }

    /**
     * 获取网络数据
     *
     * @param httpMethod 请求方式
     * @param params     请求参数
     */
    public void getHttpData(HttpMethod httpMethod, RequestParams params) {

        String url = params.toString();
        LogUtils.LogRequest(url);

        if (TextUtils.isEmpty(url)) {
            if (getHttpDataListener != null) {
                getHttpDataListener.onGetError(new Throwable(context.getString(R.string.no_set_url)));
            }
        } else {
            // TODO 设置连接超时时间
            params.setConnectTimeout(SystemConfig.TIME_OUT);
            x.http().request(httpMethod, params, new GetHttpCallBack(params));
        }
    }

    /**
     * 下载文件
     *
     * @param httpMethod 请求方式
     * @param params     请求参数
     * @param path       保存路径
     */
    public void downloadFile(HttpMethod httpMethod, RequestParams params, String path) {

        String url = params.toString();
        LogUtils.LogRequest(url);

        if (TextUtils.isEmpty(url)) {
            if (downloadListener != null) {
                downloadListener.onDownloadError(new Throwable(context.getString(R.string.no_set_url)));
            }
        } else {
            // TODO 设置连接超时时间
            params.setConnectTimeout(SystemConfig.TIME_OUT);

            if (TextUtils.isEmpty(path)) {
                // 设置下载保存路径
                params.setSaveFilePath(SystemConfig.DOWNLOAD_FILE_DIR);
            } else {
                // 设置下载保存路径
                params.setSaveFilePath(path);
            }
            // 自动命名
            params.setAutoRename(true);
            // 自动断点下载
            params.setAutoResume(true);

            x.http().request(httpMethod, params, new DownloadCallBack(params));
        }
    }

    /**
     * 下载文件
     *
     * @param httpMethod 请求方式
     * @param params     请求参数
     */
    public void downloadFile(HttpMethod httpMethod, RequestParams params) {
        downloadFile(httpMethod, params, "");
    }

    /**
     * 上传文件
     *
     * @param params
     * @param filePaths
     */
    public void uploadFile(RequestParams params, ArrayList<KeyValue> filePaths) {

        String url = params.toString();
        LogUtils.LogRequest(url);

        if (TextUtils.isEmpty(url)) {
            if (uploadListener != null) {
                uploadListener.onUploadError(new Throwable(context.getString(R.string.no_set_url)));
            }
        } else {
            // TODO 设置连接超时时间
            params.setConnectTimeout(SystemConfig.TIME_OUT);
            params.setMultipart(true);

            if (filePaths != null) {
                for (KeyValue t : filePaths) {
                    if (t.getValue() instanceof String) {
                        params.addBodyParameter(t.getKey(), new File((String) t.getValue()));
                    } else if (t.getValue() instanceof File) {
                        params.addBodyParameter(t.getKey(), (File) t.getValue());
                    }
                }
            }

            x.http().post(params, new UploadCallBack(params));
        }
    }

    /**
     * 上传多个文件
     *
     * @param params
     * @param filePaths
     */
    public void uploadFiles(RequestParams params, ArrayList<KeyValue> filePaths) {

        String url = params.toString();
        LogUtils.LogRequest(url);

        if (TextUtils.isEmpty(url)) {
            if (uploadListener != null) {
                uploadListener.onUploadError(new Throwable(context.getString(R.string.no_set_url)));
            }
        } else {
            // TODO 设置连接超时时间
            params.setConnectTimeout(SystemConfig.TIME_OUT);
            params.setMultipart(true);

            if (filePaths != null) {
                for (KeyValue t : filePaths) {
//                    if (t.getValue() instanceof String) {
//                        params.addBodyParameter(t.getKey(), new File((String) t.getValue()));
//                    } else if (t.getValue() instanceof File) {
//                        params.addBodyParameter(t.getKey(), (File) t.getValue());
//                    }
//                    params.addBodyParameter(t.getKey(), (File) t.getValue());
                    for(File file:(List<File>)t.getValue()){
                        params.addBodyParameter(t.getKey(), file);
                    }

                }
            }

            x.http().post(params, new UploadCallBack(params));
        }
    }

    /**
     * 上传文件，（默认key）
     *
     * @param params
     * @param filePaths
     */
    public void uploadFileDefault(RequestParams params, ArrayList<Object> filePaths) {

        ArrayList<KeyValue> paths = new ArrayList<>();

        if (filePaths != null) {
            for (Object t : filePaths) {
                paths.add(new KeyValue(DEFAULT_UPLOAD_FILE_KEY, t));
            }
        }

        uploadFile(params, paths);
    }

    /**
     * 设置获取图片监听
     *
     * @param loadImageListener
     */
    public void setLoadImageListener(LoadImageListener loadImageListener) {
        this.loadImageListener = loadImageListener;
    }

    /**
     * 设置获取网络数据监听
     *
     * @param getHttpDataListener
     */
    public void setGetHttpDataListener(GetHttpDataListener getHttpDataListener) {
        this.getHttpDataListener = getHttpDataListener;
    }

    /**
     * 设置下载文件监听
     *
     * @param downloadListener
     */
    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    /**
     * 设置上传文件监听
     *
     * @param uploadListener
     */
    public void setUploadListener(UploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    /*************************************** 不暴露的方法 ***************************************/

    /**
     * 获取网络数据返回结果
     *
     * @author HG
     */
    private class GetHttpCallBack implements Callback.ProgressCallback<String> {

        private RequestParams params;

        public GetHttpCallBack(RequestParams params) {
            this.params = params;
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            LogUtils.LogRequest(
                    params.toString(),
                    cex == null ? "" : cex.getMessage()
            );
            if (getHttpDataListener != null) {
                getHttpDataListener.onGetCancel(cex);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            LogUtils.LogRequest(
                    params.toString(),
                    ex == null ? "" : ex.getMessage()
            );
            if (getHttpDataListener != null) {
                getHttpDataListener.onGetError(ex);
            }
        }

        @Override
        public void onFinished() {
            if (getHttpDataListener != null) {
                getHttpDataListener.onGetFinish();
            }
        }

        @Override
        public void onSuccess(String result) {

            LogUtils.LogRequest(
                    params.toString(),
                    result
            );

            ResponseInfo responseInfo = null;
            try {
                responseInfo = new Gson().fromJson(result, ResponseInfo.class);
            } catch (Exception e) {

            }

            if (checkTokenLive(responseInfo)) {
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetSuccess(result, responseInfo);
                }
            } else {
                autoExitApp("登录授权已过期，正在重新登录……");
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isLoading) {
            if (getHttpDataListener != null) {
                getHttpDataListener.onGetLoading(total, current);
            }
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onWaiting() {

        }

    }

    /**
     * 获取图片返回结果
     *
     * @author HG
     */
    private class LoadImageCallBack implements Callback.ProgressCallback<Drawable> {

        private String url;
        private boolean isFromNet;
        private String cachePath;
        private String cacheName;
        private View view;
        private boolean isPng;

        public LoadImageCallBack(String url, boolean isFromNet, String cachePath, String cacheName, View view, boolean isPng) {
            this.url = url;
            this.isFromNet = isFromNet;
            this.cachePath = cachePath;
            this.cacheName = cacheName;
            this.view = view;
            this.isPng = isPng;
        }

        @Override
        public void onCancelled(CancelledException cex) {
            LogUtils.LogRequest(
                    url,
                    cex == null ? "" : cex.getMessage()
            );
            if (loadImageListener != null) {
                loadImageListener.onLoadCancel(view, cex);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            LogUtils.LogRequest(
                    url,
                    ex == null ? "" : ex.getMessage()
            );
            if (loadImageListener != null) {
                loadImageListener.onLoadError(view, ex);
            }
        }

        @Override
        public void onFinished() {
            if (loadImageListener != null) {
                loadImageListener.onLoadFinish(view);
            }
        }

        @Override
        public void onSuccess(Drawable drawable) {
            if (isFromNet && drawable != null) {// 从网络获取图片
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bitmap = bd.getBitmap();

                if (loadImageListener != null) {
                    loadImageListener.onLoadSuccess(view, bitmap);
                }

                savePhotoToSDCard(bitmap, cachePath, cacheName, isPng);
            } else {// 从本地获取图片
                if (loadImageListener != null) {
                    BitmapDrawable bd = (BitmapDrawable) drawable;
                    Bitmap bitmap = bd.getBitmap();

                    loadImageListener.onLoadSuccess(view, bitmap);
                }
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isLoading) {
            if (loadImageListener != null) {
                loadImageListener.onLoadLoading(view, total, current);
            }
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onWaiting() {

        }
    }

    /**
     * 下载文件返回结果
     *
     * @author HG
     */
    private class DownloadCallBack implements Callback.ProgressCallback<File> {

        private RequestParams params;

        public DownloadCallBack(RequestParams params) {
            this.params = params;
        }

        @Override
        public void onCancelled(CancelledException cex) {
            LogUtils.LogRequest(
                    params.toString(),
                    cex == null ? "" : cex.getMessage()
            );
            if (downloadListener != null) {
                downloadListener.onDownloadCancel(cex);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            LogUtils.LogRequest(
                    params.toString(),
                    ex == null ? "" : ex.getMessage()
            );
            if (downloadListener != null) {
                downloadListener.onDownloadError(ex);
            }
        }

        @Override
        public void onFinished() {
            if (downloadListener != null) {
                downloadListener.onDownloadFinish();
            }
        }

        @Override
        public void onSuccess(File file) {
            LogUtils.LogRequest(
                    params.toString(),
                    file == null ? "" : file.getAbsolutePath()
            );
            if (downloadListener != null) {
                downloadListener.onDownloadSuccess(file);
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isLoading) {
            LogUtils.LogRequest(
                    params.toString(),
                    current + "/" + total
            );
            if (downloadListener != null) {
                downloadListener.onDownloadLoading(total, current);
            }
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onWaiting() {

        }
    }

    /**
     * 上传文件返回结果
     *
     * @author HG
     */
    private class UploadCallBack implements Callback.ProgressCallback<String> {

        private RequestParams params;

        public UploadCallBack(RequestParams params) {
            this.params = params;
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            LogUtils.LogRequest(
                    params.toString(),
                    cex == null ? "" : cex.getMessage()
            );
            if (uploadListener != null) {
                uploadListener.onUploadCancel(cex);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            LogUtils.LogRequest(
                    params.toString(),
                    ex == null ? "" : ex.getMessage()
            );
            if (uploadListener != null) {
                uploadListener.onUploadError(ex);
            }
        }

        @Override
        public void onFinished() {
            if (uploadListener != null) {
                uploadListener.onUploadFinish();
            }
        }

        @Override
        public void onSuccess(String result) {
            LogUtils.LogRequest(
                    params.toString(),
                    result
            );

            ResponseInfo responseInfo = null;
            try {
                responseInfo = new Gson().fromJson(result, ResponseInfo.class);
            } catch (Exception e) {

            }

            if (checkTokenLive(responseInfo)) {
                if (uploadListener != null) {
                    uploadListener.onUploadSuccess(result);
                }
            } else {
                autoExitApp("登录授权已过期，正在重新登录……");
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isLoading) {
            LogUtils.LogRequest(
                    params.toString(),
                    current + "/" + total
            );
            if (uploadListener != null) {
                uploadListener.onUploadLoading(total, current);
            }
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onWaiting() {

        }

    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param path
     * @param name
     */
    private void savePhotoToSDCard(Bitmap bitmap, String path, String name, boolean isPng) {

        FileOutputStream b = null;

        File file = new File(path);
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        String fileName = path + name;

        try {
            b = new FileOutputStream(fileName);

            if (isPng) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkTokenLive(ResponseInfo responseInfo) {

//        if (responseInfo.getCode() == Constants.RESPONSE_CODE_TOKEN_FAIL) {
//            return false;
//        }

        return true;
    }

    public static void autoExitApp(String tips) {

//        if (!TextUtils.isEmpty(tips)) {
////            t.warning(tips);
////        }
////
////        Intent intent = new Intent(MyApplication.create().getAppContext(), LoginActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        MyApplication.create().getAppContext().startActivity(intent);
////        ArrayList<Activity> activities = new ArrayList<>();
////        activities.addAll(MyApplication.create().getAllActivity());
////
////        for (Activity t : activities) {
////            if (t.getClass() != LoginActivity.class) {
////                t.finish();
////            }
////        }
    }

}
