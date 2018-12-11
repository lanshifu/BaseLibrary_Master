package com.lanshifu.demo_module.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.notification.NotifyManager;
import com.lanshifu.demo_module.ProcessConnection;
import com.lanshifu.demo_module.receiver.ScreenReceiver;


public class MainService extends Service {
    private static final String TAG = "ScreenService";
    private ScreenReceiver mScreenOReceiver;
    private long mStartTime;
    private Uri SMS_ALL = Uri.parse("content://sms/");  //全部短信

    //    private static final int CLEAR_HISTORY_TIME = 30 * 60 * 1000;
    private static final int CLEAR_HISTORY_TIME = 10 * 1000;

    @Override
    public IBinder onBind(Intent intent) {
        LogHelper.d("MainService:onBind");
        return new ProcessConnection.Stub() {
        };
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogHelper.d("MainService:onStartCommand");
        startForeground(0, NotifyManager.getInstance(this).getNormalNotify(this,"","",null));
        //绑定建立链接
        bindService(new Intent(this, GuardService.class),
                mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //链接上
            LogHelper.d("MainService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogHelper.d("MainService:断开链接");
            //断开链接
            startService(new Intent(MainService.this, GuardService.class));
            //重新绑定
            bindService(new Intent(MainService.this, GuardService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };


    public void onCreate() {
        super.onCreate();
        LogHelper.d("MainService:oncreate");
        mStartTime = System.currentTimeMillis();
        registerReceiver();


        run();


    }

    private void registerReceiver() {
        LogHelper.d("registerReceiver");
        /* 注册屏幕唤醒/锁屏的广播 */
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        mScreenOnFilter.addAction("android.intent.action.SCREEN_ON");
        mScreenOnFilter.addAction("android.intent.action.SCREEN_OFF");
        mScreenOReceiver = new ScreenReceiver();
        MainService.this.registerReceiver(mScreenOReceiver, mScreenOnFilter);
    }


    private void run() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(5000);
                        LogHelper.d("run...");
                        if (System.currentTimeMillis() - mStartTime > CLEAR_HISTORY_TIME) {
                            LogHelper.d("run: 定时做一些事情...");
                            mStartTime = System.currentTimeMillis();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public void onDestroy() {
        super.onDestroy();
        LogHelper.d("MainService:onDestroy");
        MainService.this.unregisterReceiver(mScreenOReceiver);
    }




}
