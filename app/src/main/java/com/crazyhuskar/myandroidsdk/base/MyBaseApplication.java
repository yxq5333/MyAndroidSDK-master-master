package com.crazyhuskar.myandroidsdk.base;

import android.app.Application;

import com.crazyhuskar.myandroidsdk.util.MyUtilAppInfo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import static com.crazyhuskar.myandroidsdk.SystemConfig.isDebug;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */

public class MyBaseApplication extends Application implements IBaseApplication{
    private static MyBaseApplication myBaseApplication;
    private ArrayList<AppCompatActivity> activityAllList = new ArrayList<>();

    public static MyBaseApplication getInstance() {
        if (myBaseApplication == null) {
            //同步区块里面的代码只有在第一次才会执行
            synchronized (MyBaseApplication.class){
                if(myBaseApplication == null){
                    myBaseApplication = new MyBaseApplication();
                }
            }
        }
        return myBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myBaseApplication = bindApplication();
        isDebug = MyUtilAppInfo.isDebug(this);
        MultiDex.install(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public MyBaseApplication bindApplication() {
        return this;
    }
}
