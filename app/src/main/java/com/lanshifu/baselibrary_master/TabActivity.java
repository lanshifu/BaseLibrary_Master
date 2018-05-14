package com.lanshifu.baselibrary_master;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.BaseTabActivity;

public class TabActivity extends BaseTabActivity {



    @Override
    protected void initView() {

        String[] titles = new String[]{"tab1", "title2"};
        Fragment[] fragments = new Fragment[]{new DefaultFragment(),new DefaultFragment()};
        setupTabLayout(titles,fragments);

    }
}
