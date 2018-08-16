package com.lanshifu.baselibrary.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.BuildConfig;
import com.lanshifu.baselibrary.network.ApiConstant;

import org.litepal.LitePal;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;

/**
 * Created by Administrator on 2018\4\22 0022.
 */

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUrlManager.getInstance().putDomain("wanandroid", ApiConstant.URL_WANDROID);
        // 全局 BaseUrl 的优先级低于 Domain-Name header 中单独配置的,其他未配置的接口将受全局 BaseUrl 的影响
        RetrofitUrlManager.getInstance().setGlobalDomain(ApiConstant.URL_DERAULT);
        RetrofitUrlManager.getInstance().setDebug(BuildConfig.DEBUG);

        //换肤框架初始化
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();

//        ARouter
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        LitePal.initialize(this);

    }
}
