package com.lanshifu.baselibrary_master.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.baselibrary.base.BaseActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary_master.R;

public class MainActivity extends BaseActivity {


    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {

        addFragment(R.id.fl_container, new MainFragment());

//        request();

    }


    @Override
    protected void onResume() {
        super.onResume();
        test();
    }

    private void test() {
        TelephonyManager teleMgr = (TelephonyManager) MainApplication.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            LogHelper.d("没有2权限");
            return;
        }
        int networkType = teleMgr.getDataNetworkType();
        LogHelper.d("networkType  = " + networkType);

    }
}
