package com.lanshifu.demo_module.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.lanshifu.baselibrary.utils.CommonUtils;
import com.lanshifu.baselibrary.utils.SystemUtil;
import com.lanshifu.demo_module.DemoApplication;
import com.lanshifu.demo_module.R;

/**
 * 闪屏页
 */
public class DemoSplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_splash);

        initView(savedInstanceState);

    }


    protected void initView(Bundle savedInstanceState) {

        Log.d("lxb","进入SplashActivity initView 耗时时间：" + (System.currentTimeMillis() - DemoApplication.applicationOncreateTIme));

        //判断是否VM支持多dex



    }

    private void gotoMainActivity(){
        MultiDex.install(DemoSplashActivity.this);
        DemoApplication.applicationOncreateTIme = System.currentTimeMillis();
        startActivity(new Intent(DemoSplashActivity.this,DemoMainActivity.class));
        finish();
    }




    public void gotoMain(View view) {
        gotoMainActivity();
    }
}
