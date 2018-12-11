package com.lanshifu.activity_name_module.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.lanshifu.baselibrary.log.LogHelper;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by lanshifu on 2018/10/29.
 */

public class ScreenUtil {

    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUpAndUnlock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (!isScreenOn(context)) {
            wake(context);
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
        LogHelper.d("wakeUpAndUnlock: disableKeyguard");
    }


    /**
     * 判断屏幕是否亮
     * @param context
     * @return
     */
    public static boolean isScreenOn(Context context){
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        LogHelper.d("wakeUpAndUnlock: screenOn =  "+screenOn);
        return screenOn;
    }

    /**
     * 亮屏
     * @param context
     */
    public static void wake(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        wl.acquire(10000); // 点亮屏幕
        wl.release(); // 释放
    }

}
