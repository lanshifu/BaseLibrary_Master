package com.lanshifu.demo_module;

import android.app.Application;
import android.content.res.Resources;
import android.os.Environment;

import com.lanshifu.baselibrary.app.BaseApplication;
import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.plugin_core.PluginManager;

public class DemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //换肤,onCreate 只有在单独作为app才会调用
        initSkin(this);

    }

    @Override
    public void initModuleApp(Application application) {



        LogHelper.d("DemoApplication ->initModuleApp");

    }
}
