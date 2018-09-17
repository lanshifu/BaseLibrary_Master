package com.lanshifu.demo_module.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.plugin_core.PluginManager;

import java.io.File;
import java.lang.reflect.Method;

import butterknife.OnClick;
import dalvik.system.DexClassLoader;

public class DemoPluginActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.demo_plugin_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("插件篇");
        String plugPath = Environment.getExternalStorageDirectory()+"/plugin.apk";
        PluginManager.getInstance().install(this,plugPath);

    }
    @OnClick({R2.id.btn_load_class, R2.id.btn_load_proxy})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_load_class){
            String apkPath = Environment.getExternalStorageDirectory() + "/plugin.apk";
            loadApk(apkPath);

        }else if (view.getId() == R.id.btn_load_proxy){
            PluginManager.getInstance().startPlugin(DemoPluginActivity.this);
        }

    }

    /**
     * http://www.liuguangli.win/archives/366
     * @param apkPath
     */
    private void loadApk(String apkPath) {
        LogHelper.d("loadApk,path = " +apkPath);
        File dexOpt = this.getDir("dexOpt", MODE_PRIVATE);
        //optimizedDirectory：解压后的.dex文件的存储路径，不能为空。这个路径强烈建议使用应用程序的私有路径
        //libraryPath：os库的存放路径，可以为空，若有os库，必须填写。
        //parent：父亲加载器，一般为context.getClassLoader(),使用当前上下文的类加载器。
        DexClassLoader classloader = new DexClassLoader(apkPath, dexOpt.getAbsolutePath(),
                null, this.getClassLoader());
        LogHelper.d("Searching for class : com.lanshifu.plugin.ClassToBeLoad");

        try {
            Class<?> classToLoad = (Class<?>) classloader.loadClass("com.lanshifu.plugin.ClassToBeLoad");
            Object instance = classToLoad.newInstance();
            Method method = classToLoad.getMethod("method");
            method.invoke(instance);
            ToastUtil.showShortToast("调用插件类成功");
        } catch (Exception e) {
            ToastUtil.showLongToast(e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public Resources getResources() {
        Resources newRes =  PluginManager.getInstance().getAppResource();
        return newRes == null ? super.getResources() : newRes;
    }
}
