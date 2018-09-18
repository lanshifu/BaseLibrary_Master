package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.service.GuardService;
import com.lanshifu.demo_module.service.JobWakeUpService;
import com.lanshifu.demo_module.service.MainService;

public class DemoGuardActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("进程守护,锁屏启动此界面");

        startService(new Intent(this,MainService.class));
        startService(new Intent(this,GuardService.class));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this,JobWakeUpService.class));
        }

    }
}
