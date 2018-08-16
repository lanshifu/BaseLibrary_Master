package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;

public class DemoInstalledAppListActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.demo_installed_app_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("已安装应用信息");
    }
}
