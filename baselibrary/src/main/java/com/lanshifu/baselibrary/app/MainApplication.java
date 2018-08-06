package com.lanshifu.baselibrary.app;

import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.BuildConfig;
import com.lanshifu.baselibrary.utils.SPUtils;

import org.litepal.LitePal;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * Created by Administrator on 2018\4\22 0022.
 */

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUrlManager.getInstance().putDomain("wanandroid", "http://www.wanandroid.com");
        // 全局 BaseUrl 的优先级低于 Domain-Name header 中单独配置的,其他未配置的接口将受全局 BaseUrl 的影响
        RetrofitUrlManager.getInstance().setGlobalDomain("http://118.24.18.251:9999");

        //夜间模式设置
        if (SPUtils.getBoolean(SPUtils.KEY_THEME_DAY)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

//        ARouter
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        LitePal.initialize(this);

    }
}
