package com.crazyhuskar.myandroidsdk.util;


import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author CrazyHuskar
 * @date 2018/8/9
 */
public class MyUtilBase64 {

    /**
     * 编码
     *
     * @param text
     * @return
     */
    public static String enCoded(String text) {

        try {
            return Base64.encodeToString(text.getBytes("utf-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解码
     *
     * @param text
     * @return
     */
    public static String deCoded(String text) {
        try {
            return new String(Base64.decode(text, Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
