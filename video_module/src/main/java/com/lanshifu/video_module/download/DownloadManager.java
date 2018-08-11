package com.lanshifu.video_module.download;

import android.content.Intent;

import com.lanshifu.baselibrary.app.MainApplication;

public class DownloadManager {

    public static void download(String title,String url){
        Intent intent = new Intent(MainApplication.getContext(),DownloadIntentServie.class);
        intent.putExtra(DownloadIntentServie.KEY_TITLE,title);
        intent.putExtra(DownloadIntentServie.KEY_URL,url);
        MainApplication.getContext().startService(intent);
    }


}
