package com.lanshifu.plugin.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.lanshifu.plugin.R;
import com.lanshifu.plugin_core.BasePluginActivity;

public class AnotherActivity extends BasePluginActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plugin_another_activity);
    }
}
