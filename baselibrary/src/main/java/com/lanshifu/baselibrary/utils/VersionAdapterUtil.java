package com.lanshifu.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 版本适配工具类
 * Created by lanshifu on 2018/11/26.
 */

public class VersionAdapterUtil {

    /**
     * 检测未知应用开关是否打开
     * @param context
     * @return
     */
    public static boolean checkUnknowAppAndroidO(Context context){
        if (Build.VERSION.SDK_INT >= 26) {
            boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            return hasInstallPermission;
        }else {
            return true;
        }
    }

    /**
     * 引导用户打开安装未知应用开关
     * @param context
     * @param REQUEST_CODE_UNKNOWN_APP 请求码
     * 如果用户打开了开关，则
     *  if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            installAPK();
        }
     */
    public static void gotoOpenUnknowApp(Activity context, int REQUEST_CODE_UNKNOWN_APP){
        Uri selfPackageUri = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
        context.startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
    }
}
