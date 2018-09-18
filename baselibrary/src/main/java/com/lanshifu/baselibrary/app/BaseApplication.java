package com.lanshifu.baselibrary.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.AppManager;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.CommonUtils;

/**
 * Created by lanxiaobin on 2018/1/2.
 */

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //工具栏初始化
        CommonUtils.init(context);

        //注册自己的Activity的生命周期回调接口。
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    //声明一个监听Activity们生命周期的 ActivityLifecycleCallbacks
    // 方便管理包括第三方sdk中的activity
    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        /**
         * application下的每个Activity声明周期改变时，都会触发以下方法。
         */
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppManager.getInstance().addActivity(activity);
            //如何区别参数中activity代表你写的哪个activity。
            LogHelper.v("lxb ->onActivityCreated " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogHelper.v("lxb ->onActivityStarted " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogHelper.v("lxb ->onActivityResumed " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogHelper.v("lxb ->onActivityPaused " + activity.getClass().getSimpleName());

        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogHelper.v("lxb ->onActivityStopped " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogHelper.v("lxb ->onActivitySaveInstanceState " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getInstance().removeActivity(activity);
            LogHelper.v("lxb ->onActivityDestroyed " + activity.getClass().getSimpleName());
        }
    };
}
