package com.lanshifu.demo_module;

import android.content.res.Resources;
import android.os.Environment;

import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.plugin_core.PluginManager;

public class DemoApplication extends MainApplication {

    private  Resources oldResource;

    @Override
    public void onCreate() {
        super.onCreate();
        oldResource = super.getResources();
//        String plugPath = Environment.getExternalStorageDirectory()+"/plugin.apk";
//        PluginManager.getInstance().install(this,plugPath);

    }

    @Override
    public Resources getResources() {
        Resources newRes =  PluginManager.getInstance().getAppResource();
        return newRes == null ? super.getResources() : newRes;
    }
}
