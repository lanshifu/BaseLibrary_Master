package com.lanshifu.baselibrary.app;

import android.app.Application;

/**
 * 提供一个给 module 可以默认使用的 application，没有额外的依赖，不需要初始化其它
 * Created by lanshifu on 2018/10/17.
 */

public class DefaultApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        initSkin(this);
    }

    @Override
    public void initModuleApp(Application application) {

    }
}
