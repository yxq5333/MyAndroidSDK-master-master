package com.crazyhuskar.myandroidsdk.view.util;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.view.popupwindow.CommonPopupWindow;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author CrazyHuskar
 * @date 2018-10-11
 */
public class MyViewUtilPopWindow {

    /**
     * 向上弹出
     *
     * @param view
     * @param activity
     * @param layoutID
     * @param listener
     * @return
     */
    public CommonPopupWindow showUpPopWindow(View view, AppCompatActivity activity, int layoutID, CommonPopupWindow.ViewInterface listener) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(activity)
                .setView(layoutID)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnclickListener(listener)
                .create();
        popupWindow.showAsDropDown(view, 0, -(popupWindow.getHeight() + view.getMeasuredHeight()));
        return popupWindow;
    }

    /**
     * 向下弹出
     *
     * @param view
     * @param activity
     * @param layoutID
     * @param listener
     * @return
     */
    public CommonPopupWindow showDownPopWindow(View view, AppCompatActivity activity, int layoutID, CommonPopupWindow.ViewInterface listener) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(activity)
                .setView(layoutID)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(listener)
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAsDropDown(view);
        return popupWindow;
    }

    /**
     * 向右弹出
     *
     * @param view
     * @param activity
     * @param layoutID
     * @param listener
     * @return
     */
    public CommonPopupWindow showRightPopWindow(View view, AppCompatActivity activity, int layoutID, CommonPopupWindow.ViewInterface listener) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(activity)
                .setView(layoutID)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimLeft)
                .setViewOnclickListener(listener)
                .create();
        popupWindow.showAsDropDown(view, view.getWidth(), -view.getHeight());
        return popupWindow;
    }

    /**
     * 向左弹出
     *
     * @param view
     * @param activity
     * @param layoutID
     * @param listener
     * @return
     */
    public CommonPopupWindow showLeftPopWindow(View view, AppCompatActivity activity, int layoutID, CommonPopupWindow.ViewInterface listener) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(activity)
                .setView(layoutID)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .setViewOnclickListener(listener)
                .create();
        popupWindow.showAsDropDown(view, -popupWindow.getWidth(), -view.getHeight());
        return popupWindow;
    }

    /**
     * 全屏弹出
     *
     * @param activity
     * @param layoutID
     * @param listener
     * @return
     */
    public CommonPopupWindow showPopWindow(AppCompatActivity activity, int layoutID, CommonPopupWindow.ViewInterface listener) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(activity)
                .setView(layoutID)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(listener)
                .create();
        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
        return popupWindow;
    }
}
