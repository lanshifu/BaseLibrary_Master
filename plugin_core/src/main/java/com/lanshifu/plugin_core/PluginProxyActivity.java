package com.lanshifu.plugin_core;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginProxyActivity extends Activity {

    private Class<?> mPluginClass;

    @Override
    public void setContentView(int layoutResID) {
        // do something plugin need
        Resources resources = PluginManager.getInstance().getResources();
        XmlPullParser xmlResourceParser = resources.getLayout(layoutResID);
        View viewFromPlugin = LayoutInflater.from(this).inflate(xmlResourceParser, null);
        setContentView(viewFromPlugin);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("class");
        initPluginInstance(className);
        invokePluginOnCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        invokePluginOnPause();
    }

    private void initPluginInstance(String className) {
        try {
            mPluginClass = PluginManager.getInstance().getClassLoader().loadClass(className);
            Constructor<?> localConstructor = mPluginClass.getConstructor(new Class[]{});
            Object pluginInstance = localConstructor.newInstance(new Object[]{});
            // 把当前的代理Activity注入到插件中
            Method setProxy = mPluginClass.getMethod("setProxy",
                    new Class[]{PluginProxyActivity.class});
            setProxy.setAccessible(true);
            setProxy.invoke(pluginInstance, new Object[] { this });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invokePluginOnCreate(Bundle savedInstanceState) {
        try {
            Method onCreate = mPluginClass.getDeclaredMethod("onCreate",
                    new Class[]{Bundle.class});
            onCreate.setAccessible(true);
            onCreate.invoke(mPluginClass, new Object[] { savedInstanceState });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void invokePluginOnPause() {
        try {
            Method onPause = mPluginClass.getDeclaredMethod("onPause",
                    new Class[]{});
            onPause.setAccessible(true);
            onPause.invoke(mPluginClass, new Object[] {});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void startActivity(String className) {
        // do something plugin need
        Intent intent = new Intent(this,PluginProxyActivity.class);
        intent.putExtra("class",className);
        startActivity(intent);
    }
}
