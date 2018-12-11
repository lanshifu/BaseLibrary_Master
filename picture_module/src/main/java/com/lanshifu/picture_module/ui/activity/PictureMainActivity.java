package com.lanshifu.picture_module.ui.activity;

import android.os.Bundle;
import android.view.Window;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.picture_module.R;

@Route(path = RouterHub.PICTURE_MAIN_ACTIVITY)
public class PictureMainActivity extends BaseTitleBarActivity {

    /**
     * 要使用共享元素，要先在调用和被调用的Activity里面声明，getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
     ，注意这句话要在setContentView之前调用。也可以在主题里面声明
     <item name="android:windowContentTransitions">true</item>
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
    }

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
