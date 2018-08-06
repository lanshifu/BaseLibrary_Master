package com.lanshifu.video_module.bean;

import com.lanshifu.baselibrary.network.progress.body.ProgressInfo;

public class DownloadDurationBean {

    public DownloadDurationBean(String url, ProgressInfo progressInfo) {
        this.url = url;
        this.progressInfo = progressInfo;
    }

    public String url;
    public ProgressInfo progressInfo;
}
