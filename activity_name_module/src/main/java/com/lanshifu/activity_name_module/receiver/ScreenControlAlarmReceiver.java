package com.lanshifu.activity_name_module.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.lanshifu.activity_name_module.RxEvent;
import com.lanshifu.activity_name_module.utils.CmdUtil;
import com.lanshifu.activity_name_module.utils.ScreenUtil;
import com.lanshifu.activity_name_module.utils.UIUtil;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.log.LogUtil;

/**
 * Created by lanshifu on 2018/10/30.
 */

public class ScreenControlAlarmReceiver extends BroadcastReceiver {

    public static final String ACTION = "ScreenControlAlarmReceiver";

    public static final String EXTRR_OPEN_CLOSE = "open_close";
    Handler mHandler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())){

            if (intent.getBooleanExtra(EXTRR_OPEN_CLOSE,true)){
                //通知服务开始，亮屏，解锁
                LogUtil.d("收到闹钟广播,准备亮屏解锁...");

                ScreenUtil.wakeUpAndUnlock(context);

                mHandler.postDelayed(() -> {
                    LogUtil.d("上划解锁");
                    CmdUtil.swipUp();
                },3000);


                mHandler.postDelayed(() -> {
                    LogUtil.d("输入密码解锁");
                    CmdUtil.inputPinIfNeeded();
                    sendNoticeDelay(context);

                },5000);


            }else {
                LogUtil.d("收到闹钟广播,停止收能量任务");
                RxBus.getInstance().post(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG_START_TIME,false);
            }

        }

    }

    private void sendNoticeDelay(Context context){
        mHandler.postDelayed(() -> {
            LogUtil.d("10秒后发送通知");
            RxBus.getInstance().post(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG_START_TIME,true);
            openZhifubaoDelay(context);
        },10000);
    }

    private void openZhifubaoDelay(Context context){
        mHandler.postDelayed(() -> {
            LogUtil.d("2秒后打开支付宝");
            UIUtil.openZhifubao(context);
        },2000);
    }




}
