package com.lanshifu.demo_module.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.demo_module.ui.activity.DemoGuardActivity;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * /**
 * 锁屏的管理类叫KeyguardManager，
 * 通过调用其内部类KeyguardLockmKeyguardLock的对象的 disableKeyguard 方法可以取消系统锁屏，
 * newKeyguardLock的参数用于标识是谁隐藏了系统锁屏
 */
public class ScreenReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            LogHelper.d("onReceive: —— SCREEN_ON ——");
        } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
            LogHelper.d("onReceive: —— SCREEN_OFF ——");
            wakeUpAndUnlock(context);

        }
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
            startMainActivity(context);
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }


    private void startMainActivity(Context context) {
        Intent intent = new Intent(context, DemoGuardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
