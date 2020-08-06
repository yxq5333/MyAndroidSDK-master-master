package com.crazyhuskar.myandroidsdk.util;

import com.orhanobut.logger.Logger;

import static com.crazyhuskar.myandroidsdk.SystemConfig.isDebug;


/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */

public class MyUtilLog {
    /**
     * @param msg 内容
     */
    public static void i(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void d(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void e(String msg) {
        if (isDebug) {
            Logger.e(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void v(String msg) {
        if (isDebug) {
            Logger.v(msg);
        }
    }
}
