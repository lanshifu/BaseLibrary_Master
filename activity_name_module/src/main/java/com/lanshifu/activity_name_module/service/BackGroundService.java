package com.lanshifu.activity_name_module.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.lanshifu.activity_name_module.utils.ScreenUtil;
import com.lanshifu.baselibrary.log.LogHelper;

/**
 * 后台检查是否自动收能量，不自动则让它自动
 * Created by lanshifu on 2018/11/15.
 */

public class BackGroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(){
            @Override
            public void run() {
                //每隔30秒检测一下屏幕是否亮，不亮则点亮并解锁，跳转activity
                LogHelper.d("每隔30秒检测一下屏幕是否亮，不亮则点亮并解锁，跳转activity");
                ScreenUtil.wakeUpAndUnlock(BackGroundService.this);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
