package com.lanshifu.activity_name_module.module;

import android.os.Handler;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lanshifu.activity_name_module.service.MyAccessServices;
import com.lanshifu.activity_name_module.utils.AccessServiceUtil;
import com.lanshifu.activity_name_module.utils.UIUtil;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME;


/**
 * Created by lanshifu on 2018/10/30.
 */

public class ZhifubaoModule {

    static boolean hadScroll = false;
    private static boolean mIsInH5Actibity = false;

    private static int mMaxTimes = 300;
    public static int mCurrentCount = 0;

    public static final int MSG_BACK = 0;
    public static final int MSG_HOME = 1;
    public static final int MSG_OPEN_ZHIFUBAO = 2;

    private static class H extends Handler {
        boolean i = hadScroll;
    }

    private final static H mHandler = new H() {
        @Override
        public void handleMessage(Message msg) {
            MyAccessServices myAccessService = (MyAccessServices) msg.obj;
            switch (msg.what) {
                case MSG_BACK:
                    mIsInH5Actibity = false;
                    AccessServiceUtil.performBackClick(myAccessService);
                    break;

                case MSG_HOME:
                    mIsInH5Actibity = false;
                    myAccessService.performGlobalAction(GLOBAL_ACTION_HOME);
                    break;
                case MSG_OPEN_ZHIFUBAO:
                    mIsInH5Actibity = false;
                    UIUtil.openZhifubao(myAccessService);
                    break;
            }
        }
    };

    public static void onAccessibilityEvent(MyAccessServices myAccessServices, AccessibilityEvent event) {

        if (mCurrentCount > mMaxTimes) {
            LogHelper.d("打开次数已经最大");
            return;
        }
        String pkgName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        AccessibilityNodeInfo rootNodeInfo = event.getSource();
        if (rootNodeInfo == null) {
            return;
        }

        if (pkgName.equals("com.eg.android.AlipayGphone")){
            if ("com.eg.android.AlipayGphone.AlipayLogin".equals(className)){
                boolean click = AccessServiceUtil.clickTextViewByText(rootNodeInfo, "蚂蚁森林");
                LogHelper.d("蚂蚁森林 " + click);
                if (!click) {
                    //不在首页，点击首页，然后返回
                    LogHelper.d("支付宝不在首页界面，点击首页，然后返回");
                    AccessServiceUtil.clickTextViewByText(rootNodeInfo, "首页");
                    AccessServiceUtil.performBackClick(myAccessServices);
                }
            }else if (pkgName.equals("com.eg.android.AlipayGphone") &&
                    "com.alipay.mobile.nebulacore.ui.H5Activity".equals(className)){
                //记录打开次数
                mCurrentCount++;
                ToastUtil.showShortToast("进入蚂蚁森林，10秒后返回");
                mIsInH5Actibity = true;
                sendMessage(MSG_BACK, myAccessServices, 10000);
            }else {
                LogHelper.d("跳转到其它界面了，5秒后返回");
                sendMessage(MSG_BACK, myAccessServices, 5000);
            }
        }else if ("com.lanshifu.activity_name_module".equals(pkgName)) {
            sendMessage(MSG_OPEN_ZHIFUBAO, myAccessServices, 5000);
            ToastUtil.showShortToast("5秒后启动支付宝");
        }

    }

    public static void sendMessage(int what, Object obj, int delay) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        mHandler.removeMessages(what);
        mHandler.sendMessageDelayed(message, delay);
    }

    public static void removeHandlerMessages() {
        mHandler.removeMessages(MSG_HOME);
        mHandler.removeMessages(MSG_BACK);
        mHandler.removeMessages(MSG_OPEN_ZHIFUBAO);

    }

}
