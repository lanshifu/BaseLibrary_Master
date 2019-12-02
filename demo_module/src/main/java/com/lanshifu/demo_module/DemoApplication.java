package com.lanshifu.demo_module;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.github.moduth.blockcanary.BlockCanary;
import com.lanshifu.baselibrary.app.BaseApplication;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.utils.SystemUtil;
import com.lanshifu.demo_module.block.AppBlockCanaryContext;
import com.lanshifu.demo_module.ui.activity.DemoMainActivity;
import com.lanshifu.demo_module.ui.activity.DemoSplashActivity;
import com.lanshifu.demo_module.ui.activity.LoadDexActivity;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class DemoApplication extends BaseApplication {

    private static final String TAG = "lxb-DemoApplication";
    private HandlerThread preloadActivityHandlerThread;



    //记录onCreate时间
    public static long applicationOncreateTIme;

    @Override
    public void onCreate() {
        if (isMultiDexProcess(this)) {
            Log.d(TAG, "DemoApplication-onCreate: 非主进程，不做操作");
            return;
        }
        Log.d(TAG, "DemoApplication-onCreate: 主进程");
        super.onCreate();


        onCreateInit();

    }

    private void onCreateInit(){

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
        CrashReport.initCrashReport(context, "e5d168a687", true, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
        Bugly.init(getApplicationContext(), "8c0ef3e06a", true);
        Bugly.setIsDevelopmentDevice(getApplicationContext(), true);

        // 在主进程初始化调用
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

//        LogUtil.d("进程名：" + SystemUtil.getProcessName(this)); //com.lanshifu.demo_module

        //滴滴开源 监控工具
        DoraemonKit.install(this);

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        int myPid = Process.myPid();
        Log.d(TAG, "myPid = " + myPid + ",getPackageName = " + base.getPackageName());

        if (isMultiDexProcess(this)) {
            Log.d(TAG, "DemoApplication-attachBaseContext: 非主进程，不做操作");
            return;
        }
        Log.d(TAG, "DemoApplication-attachBaseContext: 主进程");


        //时机，如果放到闪屏页，那么所有ContextProvider必须指定在main dex，否则会Class Not Found，
        // 因为 ContextProvider 的初始化是在 attachBaseContext 之后，onCreate 之前
        //todo 判断如果在主进程，启动一个初始化MultiDex的Activity，在子线程操作，操作完finish掉，这里在继续走
        if (!SystemUtil.isVMMultidexCapable()) {
            loadMultiDex(base);
        }

        // 安装tinker
//        Beta.installTinker();

    }

    private void loadMultiDex(Context context) {
        newTempFile(context); //创建临时文件
        Intent intent = new Intent(context, LoadDexActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //检查MultiDex是否安装完（安装完会删除临时文件）
        checkIfLoadDexSuccess(context);
        long startTime = System.currentTimeMillis();
        MultiDex.install(context);
        Log.d(TAG, "loadMultiDex: 第二次耗时：" + (System.currentTimeMillis() - startTime));
    }


    //创建一个临时文件
    private void newTempFile(Context context) {
        try {
            File file = new File(context.getCacheDir().getAbsolutePath(), "load_dex.tmp");
            if (!file.exists()) {
                Log.d(TAG, "newTempFile: ");
                file.createNewFile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /**
     * 检查MultiDex是否安装完,通过判断临时文件是否被删除
     * @param context
     * @return
     */
    private void checkIfLoadDexSuccess(Context context) {
        File file = new File(context.getCacheDir().getAbsolutePath(), "load_dex.tmp");
        int i = 0;
        try {
            while (file.exists()) {
                Thread.currentThread();
                Thread.sleep(500);
                Log.d(TAG, "checkIfLoadDexSuccess: sleep count = " + ++i);
                i++;
                if (i > 20) {
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    



    private void preLoadActivity() {
        this.preloadActivityHandlerThread = new HandlerThread("aaa-1");
        this.preloadActivityHandlerThread.start();
        new Handler(this.preloadActivityHandlerThread.getLooper()).post(new Runnable() {
            public void run() {

                try {
                    long startTime = System.currentTimeMillis();
                    Log.d("lxb", "预加载Activity");
                    DemoSplashActivity splashActivity = new DemoSplashActivity();
                    DemoMainActivity mainActivity = new DemoMainActivity();
                    Log.d("lxb", "预加载Activity 耗时：" + (System.currentTimeMillis() - startTime));
                } catch (Exception exception) {
                    Log.d("lxb", "预加载失败？: " + exception.toString());
                }
            }
        });
    }

    @Override
    public void initModuleApp(Application application) {

        LogUtil.d("DemoApplication ->initModuleApp");

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

    public static boolean isMultiDexProcess(Context context){
        Log.d(TAG, "getProcessName: " + SystemUtil.getProcessName(context));
        return SystemUtil.getProcessName(context) == null || SystemUtil.getProcessName(context).endsWith(":load_dex");
    }

}
