package com.crazyhuskar.myandroidsdk.base.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * @date 2018/6/12
 */
public abstract class MyBaseModel<T> {
    /**
     * 地址
     */
    protected String url = "";
    /**
     * 表单提交参数
     */
    protected Map<String, String> params = new HashMap<>();
    /**
     * 报头header
     */
    protected Map<String, String> headers = new HashMap<>();
    /**
     * json
     */
    protected String jsonObject = "{}";
    /**
     * 文件列表
     */
    protected List<File> files = new ArrayList<>();

    public MyBaseModel url(String url) {
        this.url = url;
        return this;
    }

    public MyBaseModel params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public MyBaseModel headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public MyBaseModel jsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public MyBaseModel files(List<File> files) {
        this.files = files;
        return this;
    }

    public abstract void execute(MyCallback<T> callback);

}
