package com.lanshifu.baselibrary.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.lanshifu.baselibrary.R;

/**
 * Created by lanshifu on 2018/12/4.
 */

public class NotifyManager {
    // 单例开始
    private volatile static NotifyManager INSTANCE;

    private NotifyManager(Context context) {
        initNotifyManager(context);
    }

    private int NOTIFY_ID = 10;

    public static NotifyManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotifyManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotifyManager(context);
                }
            }
        }
        return INSTANCE;
    }
    // 单例结束

    private NotificationManager manager;
    // NotificationManagerCompat
    private NotificationCompat.Builder builder;

    //初始化通知栏配置
    private void initNotifyManager(Context context) {
        context = context.getApplicationContext();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 如果存在则清除上一个消息
//        manager.cancel(news_flag);
        builder = new NotificationCompat.Builder(context, NotifyCompat.CHANNEL_ID_1);

        // 状态栏的动画提醒语句
        builder.setTicker("动画提醒语句");
        // 什么时候提醒的
        builder.setWhen(System.currentTimeMillis());
        // 设置通知栏的优先级
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        // 设置点击可消失
        builder.setAutoCancel(true);
        // 设置是否震动等
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        // 设置icon
        builder.setSmallIcon(R.drawable.ic_launcher_round);

    }

    /**
     * 获取通知，启动前台service时候要用到
     * @param context
     * @param title
     * @param content
     * @param intent
     * @return
     */
    public Notification getNormalNotify(Context context,String title,String content,Intent intent){
        NotifyCompat.setONotifyChannel(manager,builder, NotifyCompat.CHANNEL_ID_1, NotifyCompat.CHANNEL_NAME_1);
        // 设置内容
        builder.setContentTitle(title);
        builder.setContentText(content);
        if (intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 230, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }
        return builder.build();
    }

    /**
     * 普通通知，渠道1
     * @param context
     * @param title
     * @param content
     * @param intent  跳转意图
     */
    public void showNormalNotify(Context context,String title,String content,Intent intent) {
        manager.notify(NOTIFY_ID ++ , getNormalNotify(context,title,content,intent));
    }


    /**
     * 渠道2的通知
     * @param context
     * @param title
     * @param content
     * @param intent
     */
    public void showOtherNotify(Context context,String title,String content,Intent intent) {
        NotifyCompat.setONotifyChannel(manager,builder, NotifyCompat.CHANNEL_ID_2, NotifyCompat.CHANNEL_NAME_2);
        builder.setContentTitle(title);
        builder.setContentText(content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 230, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        manager.notify(NOTIFY_ID ++, builder.build());
    }
}
