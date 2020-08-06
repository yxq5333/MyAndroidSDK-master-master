package com.crazyhuskar.myandroidsdk.view.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.crazyhuskar.myandroidsdk.view.easytransition.EasyTransition;
import com.crazyhuskar.myandroidsdk.view.easytransition.EasyTransitionOptions;

/**
 * @author CrazyHuskar
 * @date 2018-10-10
 */
public class MyViewUtilEasyTransition {
    /**
     * 共享元素跳转
     *
     * @param intent
     * @param activity
     * @param views
     */
    public static void startTransition(Intent intent, Activity activity, View... views) {
        EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(activity, views);
        EasyTransition.startActivity(intent, options);
    }

    /**
     * 共享元素承接
     *
     * @param activity
     */
    public static void enterTransition(Activity activity) {
        EasyTransition.enter(activity);
    }

    /**
     * 共享元素退出
     *
     * @param activity
     */
    public static void exitTransition(Activity activity) {
        EasyTransition.exit(activity);
    }
}
