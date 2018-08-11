package com.lanshifu.activity_name_module.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import com.lanshifu.activity_name_module.R;
import com.lanshifu.activity_name_module.R2;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;

import butterknife.BindView;

public class ShowActivityNameMainActivity extends BaseTitleBarActivity {
    @BindView(R2.id.btn_start_server)
    Button btnStartServer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_module_main;
    }

    @Override
    protected void initView(Bundle bundle) {

        setTitleText(getString(R.string.activity_name_app_name));
        hideBackIcon();

        btnStartServer.setOnClickListener(v -> {
            ToastUtil.showShortToast("启动服务");

            requestAlertWindowPermission();

        });

    }

    private static final int REQUEST_CODE = 1;

    private void requestAlertWindowPermission() {
        final String settings = Settings.ACTION_ACCESSIBILITY_SETTINGS;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                startActivity(new Intent(settings));
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else {
            startActivity(new Intent(settings));
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.i("111", "onActivityResult success");
                    String settings = Settings.ACTION_ACCESSIBILITY_SETTINGS;
                    startActivity(new Intent(settings));
                }
            }
        }
    }


}
