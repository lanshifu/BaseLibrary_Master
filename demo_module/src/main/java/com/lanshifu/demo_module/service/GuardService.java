package com.lanshifu.demo_module.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.notification.NotifyManager;
import com.lanshifu.demo_module.ProcessConnection;

/**
 * 守护服务
 */
public class GuardService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogHelper.d("GuardService:onBind");
        return new ProcessConnection.Stub() {
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogHelper.d("GuardService:onStartCommand");
        startForeground(0, NotifyManager.getInstance(this).getNormalNotify(this,"","",null));
        //绑定建立链接
        bindService(new Intent(this, MainService.class),
                mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //链接上
            LogHelper.d("GuardService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogHelper.d("GuardService:断开链接");
            //断开链接
            startService(new Intent(GuardService.this, MainService.class));
            //重新绑定
            bindService(new Intent(GuardService.this, MainService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

}
