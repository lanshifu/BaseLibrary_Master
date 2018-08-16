package com.lanshifu.picture_module.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.picture_module.R;

@Route(path = RouterHub.PICTURE_MAIN_ACTIVITY)
public class PictureMainActivity extends BaseTitleBarActivity {

    @Override
    protected void initView(Bundle bundle) {
        setTitleText("美图");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_activity_main;
    }

    @Override
    protected BaseView bindView() {
        return null;
    }


}
