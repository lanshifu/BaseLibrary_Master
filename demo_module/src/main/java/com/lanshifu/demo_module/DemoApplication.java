package com.lanshifu.demo_module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.github.moduth.blockcanary.BlockCanary;
import com.lanshifu.baselibrary.app.BaseApplication;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.demo_module.block.AppBlockCanaryContext;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //换肤,onCreate 只有在单独作为app才会调用
        initSkin(this);


        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        // 初始化Bugly
//        CrashReport.initCrashReport(context, "e5d168a687", true, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
        Bugly.init(getApplicationContext(), "e5d168a687", true);
        //
        Bugly.setIsDevelopmentDevice(getApplicationContext(), true);

        // 在主进程初始化调用哈
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        initMultiDex();
        // 安装tinker
        Beta.installTinker();
    }

    @Override
    public void initModuleApp(Application application) {

        LogHelper.d("DemoApplication ->initModuleApp");

    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
