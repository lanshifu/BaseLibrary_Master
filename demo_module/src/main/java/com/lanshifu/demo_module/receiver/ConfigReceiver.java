package com.lanshifu.demo_module.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Base64;

import com.lanshifu.baselibrary.log.LogHelper;

public class ConfigReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogHelper.d("收到config更新广播");

        getCarrierConfig(context);
    }


    public void getCarrierConfig(Context context) {
        String xmlString = null;
        Uri uri = Uri.parse("content://com.suntek.mway.carrier_configuation.provider/apis");
        ContentResolver r = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = r.query(uri, null, null, null, null);
            if (cursor != null && cursor.getCount() >0) {
                cursor.moveToFirst();
                String config = cursor.getString(0);
                LogHelper.d("加密的配置文件 ：" + config);
                xmlString = new String(Base64.decode(config, Base64.DEFAULT)); // 需要Base64解码
                LogHelper.d(xmlString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }
}
