package com.lanshifu.picture_module.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.picture_module.R;

@Route(path = RouterHub.PICTURE_MAIN_ACTIVITY)
public class PictureMainActivity extends BaseTitleBarActivity {

    @Override
    protected void initView() {

        setTitleText("美图");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_activity_main;
    }


}
