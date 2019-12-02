package com.lanshifu.demo_module.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.lanshifu.demo_module.R;

import java.io.File;

public class LoadDexActivity extends Activity {
    private static final String TAG = "lxb-LoadDexActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_loaddex);
        Log.d(TAG, "LoadDexActivity-onCreate:");

        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "MultiDex.install 开始: ");
                long startTime = System.currentTimeMillis();
                MultiDex.install(LoadDexActivity.this);
                Log.d(TAG, "MultiDex.install 结束，耗时: " + (System.currentTimeMillis() - startTime));

                deleteTempFile(LoadDexActivity.this);


                gotoSplashActivity();

            }
        }.start();
    }


    private void gotoSplashActivity(){
        finish();
        Process.killProcess(Process.myPid());
    }

    private void deleteTempFile(Context context) {
        try {
            File file = new File(context.getCacheDir().getAbsolutePath(), "load_dex.tmp");
            if (file.exists()) {
                file.delete();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
