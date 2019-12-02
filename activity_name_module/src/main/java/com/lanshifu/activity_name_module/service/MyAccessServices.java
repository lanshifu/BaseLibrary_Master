package com.lanshifu.activity_name_module.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.lanshifu.activity_name_module.RxEvent;
import com.lanshifu.activity_name_module.db.CollectionDB;
import com.lanshifu.activity_name_module.module.MainActivityModule;
import com.lanshifu.activity_name_module.module.ZhifubaoModule;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.utils.SPUtils;
import com.lanshifu.baselibrary.utils.ToastUtil;

import io.reactivex.functions.Consumer;

public class MyAccessServices extends AccessibilityService {


    private RxManager mRxManager;
    private Intent service;
    private boolean mShowPackageName;
    private boolean mZhifubaoTounengliang;

    private boolean mIsDestroy = false;
    //定时任务开始了，每天7点
    private boolean mTimerStar = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (!mShowPackageName && (!mZhifubaoTounengliang || !mTimerStar)){
            return;
        }

        if (event == null || event.getPackageName() == null || event.getClassName() == null){
            return;
        }

        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                LogUtil.d("窗口改变");
                handeWindowStateChanged(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                LogHelper.d("内容改变");
                handleWindowContentChanged(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                LogUtil.d("界面点击");
                //界面点击
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                LogUtil.d("界面文字改动");
                //界面文字改动
                break;
        }


    }

    private void handleWindowContentChanged(AccessibilityEvent event) {
        MainActivityModule.onAccessibilityEvent(MyAccessServices.this,event);
    }

    private void handeWindowStateChanged(AccessibilityEvent event) {
        String pkgName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        LogUtil.d(pkgName + "\n类名：" + className);
        if (mRxManager == null) {
            mRxManager = new RxManager();
        }
        if (mShowPackageName){
            mRxManager.post("packagename", "" + pkgName + "\n类名：" + className);
        }

        if (mZhifubaoTounengliang && mTimerStar){
            //交给支付宝module处理
            ZhifubaoModule.onAccessibilityEvent(MyAccessServices.this,event);
        }
    }


    //此方法不走
    @Override
    public void onInterrupt() {
        if (mRxManager != null) {
            mRxManager.clear();
        }
        if (service != null) {
            stopService(service);
        }
        ToastUtil.showShortToast("onInterrupt");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        ToastUtil.showShortToast("已关闭");
        if (mRxManager != null) {
            mRxManager.clear();
        }
        if (service != null) {
            stopService(service);
        }
        mIsDestroy = true;
        return super.onUnbind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        ToastUtil.showShortToast("辅助功能已打开");
//        service = new Intent(this, WindowService.class);
//        startService(service);

        initEvent();

//        initGesture();

    }

    private void initGesture() {

        Path path = new Path();

        GestureDescription.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder
                    .addStroke(new GestureDescription.StrokeDescription(path,200,200)).build();
            dispatchGesture(gestureDescription, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    LogUtil.d("滑动结束");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    LogUtil.d("滑动取消");
                }
            },null);
        }
    }


    private void initEvent() {

        mShowPackageName = SPUtils.getBoolean(RxEvent.EVENT_SHOW_PACKAGE_NAME,false);
        mZhifubaoTounengliang = SPUtils.getBoolean(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG,false);


        //显示包名开关通知
        RxBus.getInstance().register(RxEvent.EVENT_SHOW_PACKAGE_NAME, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mShowPackageName = aBoolean;
                ToastUtil.showShortToast("显示包名 " + aBoolean);
            }
        });

        //偷能量开关通知
        RxBus.getInstance().register(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mZhifubaoTounengliang = aBoolean;
                ToastUtil.showShortToast("定时偷能量 " +aBoolean);
                ZhifubaoModule.mCurrentCount = 0;
                if (!mZhifubaoTounengliang){
                    mTimerStar = false;
                }
            }
        });

        //偷能量定时改变通知
        RxBus.getInstance().register(RxEvent.EVENT_ZHIFUBAO_TOUNENGLIANG_START_TIME, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mTimerStar = aBoolean;
                ToastUtil.showShortToast("收到闹钟通知 mTimerStar = " +aBoolean);
                LogUtil.d("收到闹钟通知 mTimerStar = " +aBoolean);
                if(!mTimerStar){
                    LogUtil.d("结束任务，写入数据库");
                    int count = ZhifubaoModule.mCurrentCount;
                    CollectionDB db = new CollectionDB(System.currentTimeMillis(),count);
                    db.save();

                }
                ZhifubaoModule.mCurrentCount = 0;
            }
        });


    }

    //如果允许服务监听按键操作,该方法是按键事件的回调
    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        LogUtil.d("onKeyEvent " + event.getAction());
        return super.onKeyEvent(event);
    }
}