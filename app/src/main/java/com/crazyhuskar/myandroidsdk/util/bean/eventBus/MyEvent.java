package com.crazyhuskar.myandroidsdk.util.bean.eventBus;

import android.text.TextUtils;

import com.crazyhuskar.myandroidsdk.base.bean.MyParamKey;

import java.util.HashMap;

public class MyEvent {

    /**** 意图 ****/
    private Enum<?> eventActionCode;
    /**** 数据 ****/
    private HashMap<Enum<?>, Object> obj;

    /**
     * 构造方法
     *
     * @param eventActionCode 意图
     */
    public MyEvent(Enum<?> eventActionCode) {
        this.eventActionCode = eventActionCode;
    }

    /**
     * 构造方法
     *
     * @param eventActionCode 意图
     * @param requestCode     请求码
     */
    public MyEvent(Enum<?> eventActionCode, String requestCode) {
        this.eventActionCode = eventActionCode;
        addObj(MyParamKey.RequestCode, requestCode);
    }

    /**
     * 获取意图码
     *
     * @return Enum<?>
     */
    public Enum<?> getEventActionCode() {
        return eventActionCode;
    }

    /**
     * 设置意图码
     *
     * @param eventActionCode Enum<?>
     */
    public void setEventActionCode(Enum<?> eventActionCode) {
        this.eventActionCode = eventActionCode;
    }

    /**
     * 添加数据
     *
     * @param key   Enum<?>  键
     * @param value Object 值
     * @return MyEvent
     */
    public MyEvent addObj(Enum<?> key, Object value) {

        if (obj == null) {
            obj = new HashMap<>();
        }
        obj.put(key, value);

        return this;
    }

    /**
     * 获取数据
     *
     * @param key          Enum<?> 键
     * @param defaultValue T 默认值
     * @param <T>          根据接收类型定义返回类型
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getObj(Enum<?> key, T defaultValue) {

        if (obj == null) {
            return defaultValue;
        }

        Object o = obj.get(key);

        return o == null ? defaultValue : (T) o;
    }

    /**
     * 移除数据
     *
     * @param key String 键
     * @return MyEvent
     */
    public MyEvent removeObj(Enum<?> key) {

        if (obj != null) {
            obj.remove(key);
        }

        return this;
    }

    /**
     * 是否我请求的返回结果
     *
     * @param requestCode String 请求码
     * @return boolean
     */
    public boolean isFromMe(String requestCode) {
        return TextUtils.equals(getObj(MyParamKey.RequestCode, ""), requestCode);
    }
}
