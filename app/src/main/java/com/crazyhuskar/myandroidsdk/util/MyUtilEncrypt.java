package com.crazyhuskar.myandroidsdk.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilEncrypt {
    /**
     * MD5加密 可用于所有字符，不可解密
     *
     * @param text 内容
     * @return 加密后内容
     */
    public static String md5Encrypt(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(text.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
