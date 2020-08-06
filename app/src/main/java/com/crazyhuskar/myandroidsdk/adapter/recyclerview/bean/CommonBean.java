package com.crazyhuskar.myandroidsdk.adapter.recyclerview.bean;

public class CommonBean<T> {

    /**
     * 粘性标签
     **/
    public static final int TYPE_HEADER = 9999;

    public int itemType;
    public T data;
    public String headerName;

    /**
     * 适用于一种布局的rv
     *
     * @param data
     */
    public CommonBean(T data) {
        this.data = data;
    }

    /**
     * 适用于多种布局的普通rv
     *
     * @param itemType
     * @param data
     */
    public CommonBean(int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }

    /**
     * 适用于多种布局带粘性标签的rv
     *
     * @param itemType
     * @param data
     * @param headerName
     */
    public CommonBean(int itemType, T data, String headerName) {
        this.itemType = itemType;
        this.data = data;
        this.headerName = headerName;
    }

}