package com.crazyhuskar.myandroidsdk.util;

import java.text.SimpleDateFormat;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilTime {
    /**
     * 时间戳转换
     *
     * @param time
     * @param flag
     * @param format
     * @return
     */
    public static String timestamp2Date(String time, int flag, String format) {
        if (time == null || time.isEmpty()) {
            return "";
        }
        long longTime;
        try {
            longTime = Long.valueOf(time);
        } catch (Exception e) {
            longTime = 0L;
        }
        switch (flag) {
            case 1:
                format = "yyyy年MM月dd日 HH:mm:ss";
                break;
            case 2:
                format = "yyyy-MM-dd";
                break;
            case 3:
                format = "MM月dd日 HH:mm";
                break;
            case 4:
                break;
            default:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        return sDateFormat.format(longTime);
    }

    /**
     * 获取现在时间戳
     */
    public static long getNowTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取现在时间
     *
     * @param flag
     * @param format
     * @return
     */
    public static String getDate(int flag, String format) {
        return timestamp2Date(String.valueOf(getNowTimestamp()), flag, format);
    }

    /**
     * 将时间格式字符串转换为时间
     *
     * @param date_str
     * @param format
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
