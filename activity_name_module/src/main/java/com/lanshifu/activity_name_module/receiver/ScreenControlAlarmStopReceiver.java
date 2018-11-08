package com.lanshifu.activity_name_module.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.lanshifu.activity_name_module.RxEvent;
import com.lanshifu.activity_name_module.ui.activity.ShowActivityNameMainActivity;
import com.lanshifu.activity_name_module.utils.ScreenUtil;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.log.LogHelper;

/**
 * Created by lanshifu on 2018/10/30.
 */

public class ScreenControlAlarmStopReceiver extends BroadcastReceiver {

    public static final String ACTION = "ScreenControlAlarmStopReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())){
            LogHelper.d("收到闹钟广播,停止收能量任务");
            RxBus.getInstance().post(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG_START_TIME,false);
        }

    }

}
