package com.lanshifu.baselibrary_master.ui;

import com.lanshifu.baselibrary.base.BaseActivity;
import com.lanshifu.baselibrary_master.R;
import com.lanshifu.baselibrary_master.ui.MainFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        addFragment(R.id.fl_container,new MainFragment());

    }
}
