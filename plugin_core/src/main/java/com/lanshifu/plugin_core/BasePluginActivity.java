package com.lanshifu.plugin_core;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;

public class BasePluginActivity extends Activity {
    private PluginProxyActivity mProxy;

    public void setProxy(PluginProxyActivity proxyPluginAct){
        mProxy = proxyPluginAct;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mProxy != null){
            mProxy.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }

    }

    public void startActivity(String className) {
        mProxy.startActivity(className);
    }

    @Override
    public View findViewById(int id) {
        return mProxy.findViewById(id);
    }

    @Override
    public Resources getResources() {
        return mProxy.getResources();
    }

}
