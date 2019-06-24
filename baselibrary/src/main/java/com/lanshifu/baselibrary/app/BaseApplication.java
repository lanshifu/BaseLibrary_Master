package com.lanshifu.baselibrary.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.BuildConfig;
import com.lanshifu.baselibrary.base.AppManager;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.ApiConstant;
import com.lanshifu.baselibrary.utils.CommonUtils;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import skin.support.SkinCompatManager;
import skin.support.constraint.app.SkinConstraintViewInflater;

/**
 * Created by lanxiaobin on 2018/1/2.
 */

public abstract class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**线程池开始*/
    private static ThreadPoolExecutor pool;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int INIT_THREAD_COUNT = CPU_COUNT + 1;
    static {
        pool = new ThreadPoolExecutor(
                INIT_THREAD_COUNT,
                INIT_THREAD_COUNT,
                30L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
    public static ThreadPoolExecutor getThreadPool(){
        return pool;
    }
    /**线程池结束*/

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //工具初始化
        CommonUtils.init(context);

        //公用的库初始化
        initArouter(this);
        LitePal.initialize(this);
        initRetrofitUrlManager();


        //注册自己的Activity的生命周期回调监听
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

        initModuleApp(this);
    }

    /**
     * Application 初始化
     * @param application
     */
    public abstract void initModuleApp(Application application);


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


    /**
     * initMultiDex 支持
     */
    protected void initMultiDex() {
        MultiDex.install(this);
    }

    public static final String DOMAIN_WANANDROID = "wanandroid";
    public static final String DOMAIN_GOPEN_API = "open_api";
    protected void initRetrofitUrlManager() {
        RetrofitUrlManager.getInstance().putDomain(DOMAIN_WANANDROID, ApiConstant.URL_WANDROID);
        RetrofitUrlManager.getInstance().putDomain(DOMAIN_GOPEN_API, ApiConstant.URL_OPEN);
        // 全局 BaseUrl 的优先级低于 Domain-Name header 中单独配置的,其他未配置的接口将受全局 BaseUrl 的影响
        RetrofitUrlManager.getInstance().setGlobalDomain(ApiConstant.URL_DERAULT);
        RetrofitUrlManager.getInstance().setDebug(BuildConfig.DEBUG);
    }

    /**
     * 换肤跟background 库不能同时使用，所以先不用
     * @param application
     */
    protected void initSkin(Application application) {
        if (true){
            return;
        }
        //换肤框架初始化
        SkinCompatManager.withoutActivity(application)                         // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    protected void initArouter(Application application) {
        //ARouter
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }

    protected void initLeakCanary(Context context) {
        //内存泄漏检测
        if (LeakCanary.isInAnalyzerProcess(context)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LogHelper.d("LeakCanary.install(this);");
        LeakCanary.install(this);
    }

}
