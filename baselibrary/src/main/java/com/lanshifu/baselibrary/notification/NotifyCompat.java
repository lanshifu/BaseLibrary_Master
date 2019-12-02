package com.lanshifu.baselibrary.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.log.LogUtil;

/**
 * Created by lanshifu on 2018/12/4.
 */

public class NotifyCompat {
    public static final String CHANNEL_ID_1 = "com.lanshifu.channel1";
    public static final String CHANNEL_NAME_1 = "普通通知";

    public static final String CHANNEL_ID_2 = "com.lanshifu.channel2";
    public static final String CHANNEL_NAME_2 = "即时消息通知";


    public static void setONotifyChannel(NotificationManager manager, NotificationCompat.Builder builder, String channeId, String channelName) {
        if (TextUtils.isEmpty(channeId)|| TextUtils.isEmpty(channelName)){
            LogUtil.e("NotifyCompatYc:  ".concat("安卓8.0的通知兼容库中 channeId 与 channelName 不能为empty"));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            //第三个参数设置通知的优先级别
            NotificationChannel channel =
                    new NotificationChannel(channeId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否可以绕过请勿打扰模式
            channel.canShowBadge();//是否可以显示icon角标
            channel.enableLights(true);//是否显示通知闪灯
            channel.enableVibration(true);//收到小时时震动提示
            channel.setBypassDnd(true);//设置绕过免打扰
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET);
            channel.setLightColor(Color.RED);//设置闪光灯颜色
            channel.getAudioAttributes();//获取设置铃声设置
            channel.setVibrationPattern(new long[]{100, 200, 100});//设置震动模式
            channel.shouldShowLights();//是否会闪光
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            if (builder != null) {
                builder.setChannelId(channeId);//这个id参数要与上面channel构建的第一个参数对应
            }
        }
    }


    public static Notification getNotification(Context context, String channelId) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        return notification;
    }
}
