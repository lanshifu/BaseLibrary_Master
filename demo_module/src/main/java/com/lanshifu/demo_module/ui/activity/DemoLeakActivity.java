package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.demo_module.R;

public class DemoLeakActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("内存泄漏activity");

        leak();
    }



    //内存泄漏
    private void leak() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(10000);
                        String packageName = mContext.getPackageName();
                        LogHelper.d("内存泄漏 DemoLeakActivity" );
                    } catch (Exception e) {
                        LogHelper.e("Exception " + e.getMessage());
                    }
                }
            }
        }.start();
    }
}
