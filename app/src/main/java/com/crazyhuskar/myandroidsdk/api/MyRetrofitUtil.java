package com.crazyhuskar.myandroidsdk.api;

import android.text.TextUtils;

import com.crazyhuskar.myandroidsdk.util.MyUtilLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.crazyhuskar.myandroidsdk.SystemConfig.isDebug;


/**
 * @author CrazyHuskar
 * @date 2018/6/11
 */
public class MyRetrofitUtil {

    private static MyRetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private String baseUrl = "";

    public static MyRetrofitUtil getInstance() {
        if (sInstance == null) {
            sInstance = new MyRetrofitUtil();
        }
        return sInstance;
    }

    /**
     * 设置基础url
     *
     * @param baseUrl 基础url
     */
    public void init(String baseUrl) {
        this.baseUrl = baseUrl;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取接口
     *
     * @param tClass class类
     * @param <T>    class
     * @return 返回
     */
    public <T> T getService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    /**
     * get方式
     *
     * @param headers    头部
     * @param url        接口路径
     * @param params     参数
     * @param myCallback 监听器
     */
    public void executeGet(Map<String, String> headers, final String url, Map<String, String> params, final MyCallback<String> myCallback) {
        String paramsStr = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStr += entry.getKey() + "=" + entry.getValue() + "&";
        }
        if (TextUtils.isEmpty(paramsStr)) {
            executeGet(headers, url, myCallback);
        } else {
            paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
            executeGet(headers, url + "?" + paramsStr, myCallback);
        }
    }

    /**
     * get方式
     *
     * @param headers    头部
     * @param url        接口路径
     * @param myCallback 监听器
     */
    public void executeGet(Map<String, String> headers, final String url, final MyCallback<String> myCallback) {
        getService(MyBaseApiServer.class).executeGet(headers, url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * post方式
     *
     * @param url        接口路径
     * @param params     表单
     * @param myCallback 监听器
     */
    public void executePost(Map<String, String> headers, final String url, Map<String, String> params, final MyCallback<String> myCallback) {
        getService(MyBaseApiServer.class).executePost(headers, url, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * json方式
     *
     * @param headers
     * @param url        接口路径
     * @param jsonObject json文本
     * @param myCallback 监听器
     */
    public void executeJson(Map<String, String> headers, final String url, String jsonObject, final MyCallback<String> myCallback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject);
        getService(MyBaseApiServer.class).executeJson(headers, url, body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 上传文件和json文本
     *
     * @param headers
     * @param url        接口路径
     * @param jsonObject json 文本
     * @param files      文件
     * @param myCallback 监听器
     */
    public void uploadFiles(Map<String, String> headers, final String url, String jsonObject, List<File> files, final MyCallback<String> myCallback) {

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            requestBodyMap.put("file" + "\";filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        requestBodyMap.put("json", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject));
        getService(MyBaseApiServer.class).uploadFiles(headers, url, requestBodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 上传文件和表单
     *
     * @param headers
     * @param url        接口路径
     * @param params     表单
     * @param files      文件
     * @param myCallback 监听器
     */
    public void uploadFiles(Map<String, String> headers, final String url, Map<String, String> params, List<File> files, final MyCallback<String> myCallback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            requestBodyMap.put("file" + "\";filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        for (String key : params.keySet()) {
            requestBodyMap.put(key, RequestBody.create(MediaType.parse("text/plain"), params.get(key)));
        }
        getService(MyBaseApiServer.class).uploadFiles(headers, url, requestBodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 带进度条上传文件
     *
     * @param headers
     * @param url                接口路径
     * @param params             表单
     * @param files              文件
     * @param myProgressCallback 监听器
     */
    public void uploadFiles(Map<String, String> headers, final String url, Map<String, String> params, List<File> files, final MyProgressCallback<String> myProgressCallback) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response orginalResponse = chain.proceed(chain.request());

                return orginalResponse.newBuilder()
                        .body(new MyProgressResponseBody(orginalResponse.body(), myProgressCallback))
                        .build();
            }
        });

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            requestBodyMap.put("file" + "\";filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        for (String key : params.keySet()) {
            requestBodyMap.put(key, RequestBody.create(MediaType.parse("text/plain"), params.get(key)));
        }
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MyBaseApiServer.class).uploadFiles(headers, url, requestBodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myProgressCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myProgressCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myProgressCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myProgressCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 带进度条上传文件
     *
     * @param headers
     * @param url                接口路径
     * @param jsonObject         json 文本
     * @param files              文件
     * @param myProgressCallback 监听器
     */
    public void uploadFiles(Map<String, String> headers, final String url, String jsonObject, List<File> files, final MyProgressCallback<String> myProgressCallback) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response orginalResponse = chain.proceed(chain.request());

                return orginalResponse.newBuilder()
                        .body(new MyProgressResponseBody(orginalResponse.body(), myProgressCallback))
                        .build();
            }
        });

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            requestBodyMap.put("file" + "\";filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        requestBodyMap.put("json", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject));
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MyBaseApiServer.class).uploadFiles(headers, url, requestBodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            myProgressCallback.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            myProgressCallback.onFailure(e.getMessage());
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myProgressCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myProgressCallback.onComplete();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 下载文件
     *
     * @param headers
     * @param url                文件路径
     * @param path               保存路径
     * @param myProgressCallback 监听器
     */
    public void downloadFile(Map<String, String> headers, final String url, final String path, final String fileName, final MyProgressCallback<String> myProgressCallback) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response orginalResponse = chain.proceed(chain.request());

                return orginalResponse.newBuilder()
                        .body(new MyProgressResponseBody(orginalResponse.body(), myProgressCallback))
                        .build();
            }
        });

        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MyBaseApiServer.class).downloadFile(headers, url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (MyDownLoadManager.writeResponseBodyToDisk(path + File.separator + fileName, responseBody)) {
                            myProgressCallback.onSuccess(path + File.separator + fileName);
                        } else {
                            myProgressCallback.onFailure("下载失败");
                        }
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        myProgressCallback.onError();
                        MyUtilLog.e(url + "请求报错：" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        myProgressCallback.onComplete();
                        disposable.dispose();
                    }
                });

    }
}
