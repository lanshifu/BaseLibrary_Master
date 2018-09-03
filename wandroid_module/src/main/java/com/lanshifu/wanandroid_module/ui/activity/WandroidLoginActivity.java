package com.lanshifu.wanandroid_module.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.wanandroid_module.R;

@Route(path = RouterHub.WANDROID_LOGIN_ACTIVITY)
public class WandroidLoginActivity extends BaseTitleBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.wanandroid_login_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("玩安卓");
    }


}
