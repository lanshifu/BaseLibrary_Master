package com.lanshifu.baselibrary_master;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTabActivity;

public class TabActivity extends BaseTabActivity {



    @Override
    protected void initView(Bundle bundle) {

        String[] titles = new String[]{"tab1", "title2"};
        Fragment[] fragments = new Fragment[]{new DefaultMvpFragment(),new DefaultMvpFragment()};
        setupTabLayout(titles,fragments);

    }

}
