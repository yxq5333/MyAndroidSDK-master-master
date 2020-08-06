package com.crazyhuskar.myandroidsdk.view.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

/**
 * @author CrazyHuskar
 * @date 2018-10-10
 */
public class MyViewUtilAlertView {
    /**
     * 对话框
     *
     * @param context
     * @param title
     * @param content
     * @param cancel
     * @param ok
     * @param onItemClickListener
     */
    public static AlertView getAlertByIOS(Context context, String title, String content, String cancel, String[] ok, OnItemClickListener onItemClickListener) {
        return new AlertView(title, content, cancel, ok, null, context, AlertView.Style.Alert, onItemClickListener);
    }

    /**
     * 选择对话框
     *
     * @param context
     * @param title
     * @param content
     * @param cancel
     * @param ok
     * @param other
     * @param onItemClickListener
     */
    public static AlertView getActionSheetByIOS(Context context, String title, String content, String cancel, String[] ok, String[] other, OnItemClickListener onItemClickListener) {
        return new AlertView(title, content, cancel, ok, other, context, AlertView.Style.ActionSheet, onItemClickListener).setCancelable(true);
    }

    /**
     * 自定义对话框
     *
     * @param context
     * @param title
     * @param content
     * @param view
     * @param cancel
     * @param ok
     * @param onItemClickListener
     * @return
     */
    public static AlertView getCustomAlertByIOS(Context context, String title, String content, View view, String cancel, String[] ok, OnItemClickListener onItemClickListener) {
        return new AlertView(title, content, cancel, ok, null, context, AlertView.Style.Alert, onItemClickListener).addExtView(view);
    }

    /**
     * 对话框
     *
     * @param context
     * @param title
     * @param content
     * @param cancel
     * @param cancelOnClickListener
     * @param ok
     * @param okOnClickListener
     * @return
     */
    public static AlertDialog.Builder getAlertByAndroid(Context context, String title, String content, String cancel, DialogInterface.OnClickListener cancelOnClickListener, String ok, DialogInterface.OnClickListener okOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton(ok, okOnClickListener)
                .setNegativeButton(cancel, cancelOnClickListener);
    }

    /**
     * 多选对话框
     *
     * @param context
     * @param title
     * @param content
     * @param items
     * @param isChoose
     * @param cancel
     * @param cancelOnClickListener
     * @param ok
     * @param okOnClickListener
     * @return
     */
    public static AlertDialog.Builder getMultiChoiceAlertByAndroid(Context context, String title, String content, String[] items, boolean[] isChoose, String cancel, DialogInterface.OnClickListener cancelOnClickListener, String ok, DialogInterface.OnClickListener okOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setMessage(content)
                .setMultiChoiceItems(items, isChoose, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                })
                .setPositiveButton(ok, okOnClickListener)
                .setNegativeButton(cancel, cancelOnClickListener);
    }

    /**
     * 单选对话框
     *
     * @param context
     * @param title
     * @param content
     * @param cancel
     * @param items
     * @param cancelOnClickListener
     * @param clickOnClickListener
     * @return
     */
    public static AlertDialog.Builder getRadioAlertByAndroid(Context context, String title, String content, String cancel, String[] items, DialogInterface.OnClickListener cancelOnClickListener, DialogInterface.OnClickListener clickOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setMessage(content)
                .setItems(items, clickOnClickListener)
                .setNegativeButton(cancel, cancelOnClickListener);
    }

    /**
     * 自定义对话框
     *
     * @param context
     * @param title
     * @param content
     * @param view
     * @param cancel
     * @param ok
     * @param cancelOnClickListener
     * @param okonItemClickListener
     * @return
     */
    public static AlertDialog.Builder getCustomAlertByAndroid(Context context, String title, String content, View view, String cancel, String ok, DialogInterface.OnClickListener cancelOnClickListener, DialogInterface.OnClickListener okonItemClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setMessage(content)
                .setView(view)
                .setPositiveButton(ok, okonItemClickListener)
                .setNegativeButton(cancel, cancelOnClickListener);
    }
}
