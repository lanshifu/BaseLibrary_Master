package com.lanshifu.plugin.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.plugin.R;
import com.lanshifu.plugin_core.BasePluginActivity;
import com.lanshifu.plugin_core.PluginProxyActivity;

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
