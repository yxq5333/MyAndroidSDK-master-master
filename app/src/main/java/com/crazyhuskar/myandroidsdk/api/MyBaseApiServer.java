package com.crazyhuskar.myandroidsdk.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author CrazyHuskar
 * @date 2018/6/11
 */
public interface MyBaseApiServer {

    /**
     * get方式
     *
     * @param headers
     * @param url     接口路径
     * @return ResponseBody
     */
    @GET()
    Observable<ResponseBody> executeGet(@HeaderMap Map<String, String> headers,
                                        @Url String url);

    /**
     * post方式
     *
     * @param headers
     * @param url    接口路径
     * @param params 表单
     * @return ResponseBody
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> executePost(@HeaderMap Map<String, String> headers,
                                         @Url String url,
                                         @FieldMap Map<String, String> params);

    /**
     * post方式
     *
     * @param headers
     * @param url      接口路径
     * @param jsonBody json文本
     * @return ResponseBody
     */
    @POST()
    Observable<ResponseBody> executeJson(@HeaderMap Map<String, String> headers,
                                         @Url String url,
                                         @Body RequestBody jsonBody);

    /**
     * 上传文件
     *
     * @param headers
     * @param url  接口路径
     * @param maps 表单
     * @return ResponseBody
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@HeaderMap Map<String, String> headers,
                                         @Url String url,
                                         @PartMap() Map<String, RequestBody> maps);

    /**
     * 下载文件
     *
     * @param headers
     * @param fileUrl 文件路径
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@HeaderMap Map<String, String> headers, @Url String fileUrl);

}
