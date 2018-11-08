package com.lanshifu.activity_name_module.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lanshifu.baselibrary.log.LogHelper;

import java.util.List;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK;

/**
 * Created by lanshifu on 2018/11/5.
 */

public class AccessServiceUtil {

    /**
     * Check当前辅助服务是否启用
     *
     * @param serviceName serviceName  com.lanshifu.activity_name_module/.service.MyAccessServices
     * @return 是否启用
     */
    public static boolean isAccessibilityEnabled(Context context, String serviceName) {
        AccessibilityManager mAccessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices =
                mAccessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前往开启辅助服务界面
     */
    public static void goAccess(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 模拟点击事件
     *
     * @param nodeInfo nodeInfo
     */
    public static boolean performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                boolean click = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogHelper.d("click == " + click);
                if (click) {
                    return true;
                }
            }
            nodeInfo = nodeInfo.getParent();
        }
        return false;
    }

    /**
     * 模拟返回操作
     */
    public static void performBackClick(AccessibilityService service) {
        performBackClick(service, 500);
    }

    /**
     * 模拟返回操作
     */
    public static void performBackClick(AccessibilityService service, long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.performGlobalAction(GLOBAL_ACTION_BACK);
    }


    /**
     * 模拟下滑操作
     */
    public static void performScrollBackward(AccessibilityService service) {
        performScrollBackward(service, 500);
    }

    /**
     * 模拟下滑操作
     */
    public static void performScrollBackward(AccessibilityService service, long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    /**
     * 模拟上滑操作
     */
    public static void performScrollForward(AccessibilityService service) {
        performScrollForward(service, 500);
    }

    /**
     * 模拟上滑操作
     */
    public static void performScrollForward(AccessibilityService service, long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    /**
     * 查找对应文本的View
     *
     * @param text text
     * @return View
     */
    public static AccessibilityNodeInfo findViewByText(AccessibilityService service, String text) {
        return findViewByText(service,text, false);
    }

    /**
     * 查找对应文本的View
     *
     * @param text      text
     * @param clickable 该View是否可以点击
     * @return View
     */
    public static AccessibilityNodeInfo findViewByText(AccessibilityService service, String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     * 查找对应ID的View
     *
     * @param id id
     * @return View
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findViewByID(AccessibilityNodeInfo accessibilityNodeInfo, String id) {
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param accessibilityNodeInfo
     * @param text
     * @return
     */
    public static boolean clickTextViewByText(AccessibilityNodeInfo accessibilityNodeInfo, String text) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            LogHelper.d("包含关键字 " + text);
            boolean clickResult = false;
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    boolean click = performViewClick(nodeInfo);
                    if (click) {
                        clickResult = true;
                    }
                }
            }
            return clickResult;
        }
        return false;
    }

    public static boolean performLongClick(AccessibilityNodeInfo info, String className) {
        if (info == null) {
            return false;
        }
        if (info.isLongClickable()) {
            if (info.getClassName().equals(className)) {
                return info.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
            }
        }

        if (info.getChildCount() > 0) {
            for (int i = 0; i < info.getChildCount(); i++) {
                boolean longClick = performLongClick(info.getChild(i), className);
                if (longClick) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void clickTextViewByID(AccessibilityService service, String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
    }

    /**
     * 模拟输入
     *
     * @param text text
     */
    public static void inputText(AccessibilityService service, AccessibilityEvent event, String text) {
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle arguments = new Bundle();
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ClipboardManager clipboard = (ClipboardManager) service.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
    }


}
