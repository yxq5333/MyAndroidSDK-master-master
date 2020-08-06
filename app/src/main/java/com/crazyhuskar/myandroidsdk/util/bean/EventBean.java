package com.crazyhuskar.myandroidsdk.util.bean;


/**
 * @author CrazyHuskar
 * @date 2018/6/11
 */
public class EventBean {
    private String msg;
    private int flag;

    public EventBean(String msg, int flag) {
        this.msg = msg;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }
}
