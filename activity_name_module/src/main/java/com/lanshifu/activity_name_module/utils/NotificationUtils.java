package com.lanshifu.activity_name_module.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.PixelFormat;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.lanshifu.activity_name_module.R;


public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context){
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            getManager().notify(1,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(1,notification);
        }
    }

    public Notification getNotication(String title, String content){
        Notification notification;
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            notification = getChannelNotification
                    (title, content).build();
        }else{
            notification = getNotification_25(title, content).build();
        }
        return notification;
    }


    public static void showNotification(Context context) {

        // 获取Service
        final WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final View view = View.inflate(context,R.layout.layout_notification, null);
        final ObjectAnimator o = ObjectAnimator.ofFloat(view, "translationY", -150, 0f);
        o.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                final ObjectAnimator o2 = ObjectAnimator.ofFloat(view, "translationY", 0f, -150f);
                o2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mWindowManager.removeView(view);
                        o2.cancel();
                    }
                });

                o2.setDuration(1000).setStartDelay(1500);//执行一秒 ， 延迟1.5秒
                o2.start();
                o.cancel();

            }
        });
        o.setDuration(1000).start();


        // 设置窗口类型，一共有三种Application windows, Sub-windows, System windows
        WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
        // API中以TYPE_开头的常量有23个
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        ;
        // 设置期望的bitmap格式
        mWindowParams.format = PixelFormat.TRANSLUCENT; //设置背景透明

        // 以下属性在Layout Params中常见重力、坐标，宽高
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;

        mWindowParams.x = 0;
        mWindowParams.y = 0;

        mWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowParams.height = WindowManager.LayoutParams. WRAP_CONTENT;//WindowManager.LayoutParams. WRAP_CONTENT;

        // 添加指定视图
        mWindowManager.addView(view, mWindowParams);
    }

}