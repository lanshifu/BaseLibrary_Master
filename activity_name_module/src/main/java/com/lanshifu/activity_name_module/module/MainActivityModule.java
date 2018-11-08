package com.lanshifu.activity_name_module.module;

import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lanshifu.activity_name_module.service.MyAccessServices;
import com.lanshifu.activity_name_module.utils.AccessServiceUtil;
import com.lanshifu.baselibrary.log.LogHelper;

/**
 * Created by lanshifu on 2018/11/5.
 */

public class MainActivityModule {

    static Handler mHandler = new Handler();

    public static void onAccessibilityEvent(MyAccessServices services, AccessibilityEvent event) {

        String pkgName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        AccessibilityNodeInfo rootNodeInfo = event.getSource();
        if (rootNodeInfo == null) {
            return;
        }

        if ("com.lanshifu.activity_name_module".equals(pkgName)){
            LogHelper.d("自动输入文本");

            LogHelper.d(className);
            if ("android.widget.EditText".equals(className)){
                AccessServiceUtil.inputText(services,event,"自动输入文本");
            }
//            if ("com.lanshifu.activity_name_module.ui.activity.CollectionLogActivity".equals(className)){
//                mHandler.postDelayed(() -> {
//                    LogHelper.d("自动滚动");
//                    AccessServiceUtil.performScrollForward(services);
//                },5000);
//            }



        }
    }

}
