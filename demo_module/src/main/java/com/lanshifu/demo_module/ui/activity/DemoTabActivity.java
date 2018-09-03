package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lanshifu.baselibrary.base.activity.BaseTabActivity;
import com.lanshifu.demo_module.ui.fragment.DemoTabFragment;

public class DemoTabActivity extends BaseTabActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        String[] titles = new String[]{"tab1", "title2"};
        Fragment[] fragments = new Fragment[]{new DemoTabFragment(),new DemoTabFragment()};
        setupTabLayout(titles,fragments);
    }
}
