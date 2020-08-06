package com.crazyhuskar.myandroidsdk.util;

import android.content.Intent;
import android.os.Bundle;

import com.crazyhuskar.myandroidsdk.ActivityAnim;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author CrazyHuskar
 * @date 2018-10-27
 */
public class MyUtilActivity {
    private static MyUtilActivity myUtilActivity;
    private long exitTime = 0;

    public static MyUtilActivity getInstance() {
        if (myUtilActivity == null) {
            myUtilActivity = new MyUtilActivity();
        }
        return myUtilActivity;
    }

    public void startActivity(AppCompatActivity appCompatActivity, Class<?> cls) {
        startActivity(appCompatActivity, cls, null);
    }

    public void startActivity(AppCompatActivity appCompatActivity, Class<?> cls, Bundle bundle) {
        startActivity(appCompatActivity, cls, bundle, -1);
    }

    public void startActivity(AppCompatActivity appCompatActivity, Class<?> cls, Bundle bundle, int flag) {
        Intent intent = new Intent(appCompatActivity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (flag != -1) {
            intent.setFlags(flag);
        }
        appCompatActivity.startActivity(intent);
        appCompatActivity.overridePendingTransition(ActivityAnim.NOW_ACTIVITY_IN, ActivityAnim.NOW_ACTIVITY_OUT);
    }

    public void finishActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.finish();
        appCompatActivity.overridePendingTransition(ActivityAnim.NOW_ACTIVITY_FINISH_IN, ActivityAnim.NOW_ACTIVITY_FINISH_OUT);
    }

    public void outOfApplication(AppCompatActivity appCompatActivity) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MyUtilToast.showShort(appCompatActivity, "再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finishActivity(appCompatActivity);
            System.exit(0);
        }
    }
}
