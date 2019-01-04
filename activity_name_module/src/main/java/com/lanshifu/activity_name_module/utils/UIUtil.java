package com.lanshifu.activity_name_module.utils;

import android.content.Context;
import android.content.Intent;

public class UIUtil {

    public static void openZhifubao(Context context){
        startAppLauncher(context,"com.eg.android.AlipayGphone");
    }

    public static void startAppLauncher(Context context,String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
