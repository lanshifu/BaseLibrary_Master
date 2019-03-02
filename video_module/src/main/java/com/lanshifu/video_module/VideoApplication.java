package com.lanshifu.video_module;

import android.app.Application;

import com.lanshifu.baselibrary.app.BaseApplication;
import com.liulishuo.filedownloader.FileDownloader;

public class VideoApplication extends BaseApplication {
    @Override
    public void initModuleApp(Application application) {

        //下载库初始化
        FileDownloader.setup(this);
    }
}
