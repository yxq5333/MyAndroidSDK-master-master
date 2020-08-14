package com.crazyhuskar.myandroidsdk;


import com.crazyhuskar.myandroidsdk.base.MyBaseApplication;
import com.crazyhuskar.myandroidsdk.util.MyUtilAppInfo;
import com.crazyhuskar.myandroidsdk.util.MyUtilSDCard;

import kotlin.jvm.JvmField;

/**
 * 系统常量
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class SystemConfig {


    public static final int EXITAPP = -100;


    public static boolean isDebug = true;
    /**
     * 是否是开发模式 true:debug模式 ;false:release模式
     */
//    @JvmField
//    var IS_DEBUG_MODEL = false
    public static boolean  IS_DEBUG_MODEL = MyUtilAppInfo.isDebug(MyBaseApplication.getInstance().getApplicationContext());
    /**
     * SD卡根目录
     */
    public static final String SDCARD_DIR = MyUtilSDCard.getSDCardPath() + "/APP/";
    /**
     * 图片缓存目录
     */
    public static final String PHOTO_CACHE_DIR = SDCARD_DIR + "/ImageCache/";
    /**
     * 文件下载目录
     */
    public static final String DOWNLOAD_FILE_DIR = SDCARD_DIR + "/Download/";
    /**
     * 保存在手机里面的文件名
     */
    public static final String SP_FILE_NAME = "share_data";

    /**
     * 拍照请求
     */
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    /**
     * 录像请求
     */
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 101;
    /**
     * 权限请求
     */
    public static final int MY_PERMISSION_REQUEST_CODE = 200;
    /**
     * 请求成功
     */
    public static final String REQUEST_SUCCESS = "S";
    /**
     * 请求失败
     */
    public static final String REQUEST_FAILED = "E";
    /**
     * 请求返回值--成功
     */
    public static final int RETURN_SUCCESS = 1;
    /**
     * 请求返回值--失败
     */
    public static final int RETURN_ERROR = -1;
    /**
     * 倒计时长 60秒
     */
    public static final int TIME_COUNT = 60000;
    /**
     * xUtils超时重连时间数 单位：ms
     */
    public static final int  TIME_OUT = 30 * 1000;

    // /**** 本地数据库 ****/
    /**
     * 当前数据库DB的版本 初始是2
     */
    public static int DB_VERSION = 2;
    /**
     * 数据库名称
     */
    public static String DATABASE_NAME = "data_base";
}
