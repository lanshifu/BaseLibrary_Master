package com.lanshifu.baselibrary.app;

import android.app.Application;

import com.lanshifu.baselibrary.log.LogUtil;

/**
 * Created by Administrator on 2018\4\22 0022.
 */

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("MainApplication ->onCreate");


        initSkin(this); //换肤
        initMultiDex();

    }

    @Override
    public void initModuleApp(Application application) {
        //反射调用各个模块的 initModuleApp 方法
        for (String moduleAppName : AppConfig.moduleApps){
            try {
                Class clazz = Class.forName(moduleAppName);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleApp(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }


}
