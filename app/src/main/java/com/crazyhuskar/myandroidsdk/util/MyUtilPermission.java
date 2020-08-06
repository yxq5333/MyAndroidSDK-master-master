package com.crazyhuskar.myandroidsdk.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import androidx.annotation.NonNull;

import static com.crazyhuskar.myandroidsdk.SystemConfig.MY_PERMISSION_REQUEST_CODE;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilPermission {

    private Activity activity;
    private PermissionCallback mcallback;

    private static MyUtilPermission myUtilPermission;

    public static MyUtilPermission getInstance() {
        if (myUtilPermission == null) {
            myUtilPermission = new MyUtilPermission();
        }
        return myUtilPermission;
    }


    public MyUtilPermission init(Activity activity, PermissionCallback callback) {
        this.activity = activity;
        this.mcallback = callback;
        return myUtilPermission;
    }

    public void request(String... permissions) {
        AndPermission.with(activity)
                .requestCode(MY_PERMISSION_REQUEST_CODE)
                .permission(permissions)
                .callback(permissionListener)
                .rationale(rationaleListener)
                .start();
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case MY_PERMISSION_REQUEST_CODE:
                    mcallback.onSucceed();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case MY_PERMISSION_REQUEST_CODE:
                    mcallback.onFail();
                    break;
                default:
                    break;
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                openAppDetails();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            openAppDetails();
        }
    };

    /**
     * 打开 APP 的详情设置
     */
    public void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("我需要的权限被您禁止，否则我将无法为您继续服务，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                activity.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    public interface PermissionCallback {
        void onSucceed();

        void onFail();
    }

}
