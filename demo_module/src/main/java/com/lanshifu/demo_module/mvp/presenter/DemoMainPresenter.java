package com.lanshifu.demo_module.mvp.presenter;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.GsonUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.demo_module.bean.AppInfo;
import com.lanshifu.demo_module.bean.VideoListItemBean;
import com.lanshifu.demo_module.mvp.view.DemoMainView;
import com.lanshifu.demo_module.network.DemoApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DemoMainPresenter extends BasePresenter<DemoMainView> {

    public void test(){
        mView.textResult("哈哈哈");
    }


    //只能更新单个文件，文件夹无效
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

    public void request(){
        RetrofitHelper.getInstance().createApi(DemoApi.class)
                .request(1,10,0)
                .compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<HttpResult>() {
                    @Override
                    public void _onNext(HttpResult result) {
                        ToastUtil.showShortToast(result.message);
                        LogHelper.d("结果："+result.data);
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast(e);
                        LogHelper.e("error："+e);
                    }
                });
    }

}
