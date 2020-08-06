package com.crazyhuskar.myandroidsdk.view.util;

import android.graphics.Color;
import android.text.TextUtils;

import com.crazyhuskar.myandroidsdk.view.SlantedTextView;


/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */

public class MyViewUtilSlantedTextview {
    /**
     * 设置角标
     *
     * @param view      组件id(必填)
     * @param text      角标文字 (必填)
     * @param bgColor   角标背景颜色
     * @param textColor 角标文字颜色
     * @param flag      1:左上空;2:左上全;3:左下空;4:左下全;5:右上空;6:右上全;7:右下空;8:右上全;
     * @return
     */
    public static void setContent(SlantedTextView view, String text, String bgColor, String textColor, int flag) {
        view.setText(text);
        if (TextUtils.isEmpty(bgColor)) {
            view.setSlantedBackgroundColor(Color.parseColor("#00BFFF"));
        } else {
            view.setSlantedBackgroundColor(Color.parseColor(bgColor));
        }
        if (TextUtils.isEmpty(textColor)) {
            view.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            view.setTextColor(Color.parseColor(textColor));
        }
        switch (flag) {
            case 1:
                view.setMode(SlantedTextView.MODE_LEFT);
                break;
            case 2:
                view.setMode(SlantedTextView.MODE_LEFT_TRIANGLE);
                break;
            case 3:
                view.setMode(SlantedTextView.MODE_LEFT_BOTTOM);
                break;
            case 4:
                view.setMode(SlantedTextView.MODE_LEFT_BOTTOM_TRIANGLE);
                break;
            case 5:
                view.setMode(SlantedTextView.MODE_RIGHT);
                break;
            case 6:
                view.setMode(SlantedTextView.MODE_RIGHT_TRIANGLE);
                break;
            case 7:
                view.setMode(SlantedTextView.MODE_RIGHT_BOTTOM);
                break;
            case 8:
                view.setMode(SlantedTextView.MODE_RIGHT_BOTTOM_TRIANGLE);
                break;
            default:
                break;
        }
    }
}
