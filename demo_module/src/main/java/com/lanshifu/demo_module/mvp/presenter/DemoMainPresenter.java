package com.lanshifu.demo_module.mvp.presenter;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.demo_module.mvp.view.DemoMainView;

import java.io.File;

public class DemoMainPresenter extends BasePresenter<DemoMainView> {

    public void test(){
        mView.textResult("哈哈哈");
    }


    public void updateMedia(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/aaa/胖妞.gif";
        LogHelper.d("path = " + path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            MediaScannerConnection.scanFile(mContext, new String[]{path}, null,
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {
                    ToastUtil.showShortToast("onMediaScannerConnected");
                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    ToastUtil.showShortToast("onScanCompleted");
                }
            });
        }else {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(new File(path))));
        }

        UIUtil.snackbarText("刷新媒体库中，请稍后查看");
    }
}
