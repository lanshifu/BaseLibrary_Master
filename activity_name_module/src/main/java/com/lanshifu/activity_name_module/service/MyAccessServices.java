package com.lanshifu.activity_name_module.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;

public class MyAccessServices extends AccessibilityService {


    private RxManager mRxManager;
    private Intent service;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        String pkgName = event.getPackageName().toString();
        String className = event.getClassName().toString();

//        ToastUtil.showShortToast(className);
//        L.e("111","pkgName:"+pkgName);

        if (mRxManager == null) {
            mRxManager = new RxManager();
        }
        LogHelper.d(pkgName + "\n类名：" + className);
        mRxManager.post("packagename", "" + pkgName + "\n类名：" + className);

    }

    //此方法不走
    @Override
    public void onInterrupt() {
        if (mRxManager != null) {
            mRxManager.clear();
        }
        if (service != null) {
            stopService(service);
        }
        ToastUtil.showShortToast("onInterrupt");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        ToastUtil.showShortToast("已关闭");
        if (mRxManager != null) {
            mRxManager.clear();
        }
        if (service != null) {
            stopService(service);
        }
        return super.onUnbind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 500;
        setServiceInfo(info);
        setServiceInfo(info);
        super.onServiceConnected();
        ToastUtil.showShortToast("已连接");

        service = new Intent(this, WindowService.class);
        startService(service);

    }


}