package com.lanshifu.activity_name_module.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.lanshifu.activity_name_module.R;
import com.lanshifu.activity_name_module.R2;
import com.lanshifu.activity_name_module.RxEvent;
import com.lanshifu.activity_name_module.module.ZhifubaoModule;
import com.lanshifu.activity_name_module.receiver.ScreenControlAlarmReceiver;
import com.lanshifu.activity_name_module.receiver.ScreenControlAlarmStopReceiver;
import com.lanshifu.activity_name_module.service.WindowService;
import com.lanshifu.activity_name_module.utils.AccessServiceUtil;
import com.lanshifu.activity_name_module.utils.NotificationUtils;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.utils.SPUtils;
import com.lanshifu.baselibrary.utils.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class ShowActivityNameMainActivity extends BaseTitleBarActivity implements
        TimePickerDialog.OnTimeSetListener{
    @BindView(R2.id.btn_start_server)
    Button mBtnStartServer;
    @BindView(R2.id.switch_open_package_name)
    Switch mSwitchOpenPackageName;
    @BindView(R2.id.switch_auto_collection)
    Switch mSwitchAutoCollection;
    @BindView(R2.id.tv_auto_collection_time)
    TextView mTvAutoCollectionTime;

    private LinearLayout mLlSetting;
    private Intent mService;
    private int mStartHour;
    private int mStartMinute;

    private int mEndHour;
    private int mEndMinute;

    private static final String SP_KEY_START_HOUR = "StartHour";
    private static final String SP_KEY_START_MINUTE = "StartMinut";

    private static final String SP_KEY_END_HOUR = "EndHour";
    private static final String SP_KEY_END_MINUTE = "EndMinut";

    //闹钟开始和关闭的标志
    private final static int REQUEST_CODE_ALARM_START = 0;
    private final static int REQUEST_CODE_ALARM_STOP = 1;

    private Handler mHandler = new Handler();
    private boolean mServiceEnabled;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_module_main;
    }

    @Override
    protected void initView(Bundle bundle) {

        hideBackIcon();

        setTitleText(getString(R.string.activity_name_app_name));

        requestWindowPermission();

        initNotificationEvent();

        boolean showPackageNameItemCheck = SPUtils.getBoolean(RxEvent.EVENT_SHOW_PACKAGE_NAME, false);
        boolean zhifubaoItemCheck = SPUtils.getBoolean(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG, false);
        mStartHour = SPUtils.getInt(SP_KEY_START_HOUR, 7);
        mStartMinute = SPUtils.getInt(SP_KEY_START_MINUTE, 0);
        mEndHour = SPUtils.getInt(SP_KEY_END_HOUR, 7);
        mEndMinute = SPUtils.getInt(SP_KEY_END_MINUTE, 30);

        mSwitchOpenPackageName.setChecked(showPackageNameItemCheck);
        mSwitchOpenPackageName.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ToastUtil.showShortToast("" + isChecked);
            SPUtils.putBoolean(RxEvent.EVENT_SHOW_PACKAGE_NAME, isChecked);
            RxBus.getInstance().post(RxEvent.EVENT_SHOW_PACKAGE_NAME, isChecked);
            if (isChecked) {
                mService = new Intent(ShowActivityNameMainActivity.this, WindowService.class);
                startService(mService);
            } else {
                if (mService != null) {
                    stopService(mService);
                }
            }
        });
        mSwitchAutoCollection.setChecked(zhifubaoItemCheck);
        mSwitchAutoCollection.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SPUtils.putBoolean(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG, isChecked);
            RxBus.getInstance().post(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG, isChecked);
            if (isChecked) {
                ToastUtil.showShortToast("定时开始 " + mStartHour + ":" + mStartMinute + "---" + mEndHour + ":" + mEndMinute);
                startTimer(ShowActivityNameMainActivity.this, mStartHour, mStartMinute, 0, true, REQUEST_CODE_ALARM_START);
                startTimer(ShowActivityNameMainActivity.this, mEndHour, mEndMinute, 0, false, REQUEST_CODE_ALARM_STOP);
            } else {
                stopTimer(ShowActivityNameMainActivity.this);
                //移除延迟动作
                ZhifubaoModule.removeHandlerMessages();
            }
        });

        mTvAutoCollectionTime.setText(mStartHour + ":" + mStartMinute + "---" + mEndHour + ":" + mEndMinute);


    }

    private static final int REQUEST_CODE = 1;
    private void requestAlertWindowPermission() {
        final String settings = Settings.ACTION_ACCESSIBILITY_SETTINGS;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                startActivity(new Intent(settings));
                ToastUtil.showLongToast("找到 " + getResources().getString(R.string.activity_name_app_name)
                        + " 开关");
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);

            }
        } else {
            startActivity(new Intent(settings));
            ToastUtil.showLongToast("找到 " + getResources().getString(R.string.activity_name_app_name)
                    + " 开关");
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.i("111", "onActivityResult success");
                    String settings = Settings.ACTION_ACCESSIBILITY_SETTINGS;
                    startActivity(new Intent(settings));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    private void updateServiceStatus() {
        // 默认服务不可用
        mServiceEnabled = AccessServiceUtil.isAccessibilityEnabled(this,"com.lanshifu.activity_name_module/.service.MyAccessServices");
        if (mServiceEnabled) {
            mBtnStartServer.setText("关闭插件(总开关)");
            mBtnStartServer.setTextColor(getResources().getColor(R.color.green));
            // Prevent screen from dimming
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            mBtnStartServer.setText("开启插件（总开关）");
            mBtnStartServer.setTextColor(getResources().getColor(R.color.red));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }


    private void startTimer(Context context, int hh, int mm, int ss, boolean openOrClose, int requestCode) {
        /**
         * 16:51:00
         */
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, hh);//小时
        instance.set(Calendar.MINUTE, mm);//分钟
        instance.set(Calendar.SECOND, ss);//秒

        Intent alarmIntent;
        AlarmManager alarmService = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (openOrClose) {
            alarmIntent = new Intent(context, ScreenControlAlarmReceiver.class)
                    .setAction(ScreenControlAlarmReceiver.ACTION);
        } else {
            alarmIntent = new Intent(context, ScreenControlAlarmStopReceiver.class)
                    .setAction(ScreenControlAlarmStopReceiver.ACTION);
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(context, requestCode, alarmIntent, 0);//通过广播接收
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //低电量仍然能执行闹钟任务
            alarmService.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
            alarmService.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        } else {
            alarmService.set(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        }
    }

    private void stopTimer(Context context) {
        AlarmManager alarmService = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntentStart = new Intent(context, ScreenControlAlarmReceiver.class)
                .setAction(ScreenControlAlarmReceiver.ACTION);
        Intent alarmIntentEnd = new Intent(context, ScreenControlAlarmStopReceiver.class)
                .setAction(ScreenControlAlarmStopReceiver.ACTION);
        PendingIntent broadcast_start = PendingIntent.getBroadcast(context, REQUEST_CODE_ALARM_START, alarmIntentStart, 0);
        PendingIntent broadcast_end = PendingIntent.getBroadcast(context, REQUEST_CODE_ALARM_STOP, alarmIntentEnd, 0);
        alarmService.cancel(broadcast_start);
        alarmService.cancel(broadcast_end);
    }


    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setTabIndicators("开始时间", "结束时间");
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

        mStartHour = hourOfDay;
        mStartMinute = minute;
        mEndHour = hourOfDayEnd;
        mEndMinute = minuteEnd;

        mTvAutoCollectionTime.setText(mStartHour + ":" + mStartMinute + "---" + mEndHour + ":" + mEndMinute);
        ToastUtil.showShortToast(mStartHour + ":" + mStartMinute + "---" + mEndHour + ":" + mEndMinute);

        SPUtils.putInt(SP_KEY_START_HOUR, mStartHour);
        SPUtils.putInt(SP_KEY_START_MINUTE, mStartMinute);
        SPUtils.putInt(SP_KEY_END_HOUR, mEndHour);
        SPUtils.putInt(SP_KEY_END_MINUTE, mEndMinute);

        stopTimer(this);
        startTimer(ShowActivityNameMainActivity.this, mStartHour, mStartMinute, 0, true, REQUEST_CODE_ALARM_START);
        startTimer(ShowActivityNameMainActivity.this, mEndHour, mEndMinute, 0, false, REQUEST_CODE_ALARM_STOP);


    }

    private void requestWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 100);
            }
        }
    }

    private void initNotificationEvent() {

        RxBus.getInstance().register(RxEvent.EVENT_SHOW_NOTIFICATION, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                NotificationUtils.showNotification(ShowActivityNameMainActivity.this);
            }
        });
    }


    @Override
    protected boolean isNeedStatusBarSkin() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(RxEvent.EVENT_SHOW_NOTIFICATION);
    }


    @OnClick({R2.id.btn_start_server, R2.id.ll_collection_time_setting, R2.id.ll_goto_collection_log})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_start_server){
            requestAlertWindowPermission();
        }else if (id == R.id.ll_collection_time_setting){
            showTimePicker();

        }else if (id == R.id.ll_goto_collection_log){
            startActivity(new Intent(ShowActivityNameMainActivity.this, CollectionLogActivity.class));

        }
    }
}
