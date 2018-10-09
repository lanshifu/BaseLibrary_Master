package com.lanshifu.video_module.download;

import android.content.Context;
import android.content.Intent;


public class DownloadManager {

    public static void download(Context context,String title, String url){
        Intent intent = new Intent(context,DownloadIntentServie.class);
        intent.putExtra(DownloadIntentServie.KEY_TITLE,title);
        intent.putExtra(DownloadIntentServie.KEY_URL,url);
        context.startService(intent);
    }


}
