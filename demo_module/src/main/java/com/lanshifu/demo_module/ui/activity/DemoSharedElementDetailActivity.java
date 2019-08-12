package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.demo_module.R;

/**
 * Created by lanshifu on 2018/11/12.
 */

public class DemoSharedElementDetailActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.text_detail);
        slide.addTarget(R.id.text_close);
        slide.addTarget(R.id.view_separator);
        getWindow().setEnterTransition(slide);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.demo_shared_element_detail_activity;
    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.demo_shared_element_detail_activity;
//    }


    @Override
    protected void initView(Bundle savedInstanceState) {



    }
}
