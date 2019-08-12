package com.lanshifu.plugin.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;

import com.lanshifu.plugin.R;
import com.lanshifu.plugin_core.BasePluginActivity;

public class PluginMainActivity extends BasePluginActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_main_activity);

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AnotherActivity.class.getName());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
