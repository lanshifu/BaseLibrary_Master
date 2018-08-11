package com.lanshifu.video_module.bean;

public class DownloadDurationBean {

    public DownloadDurationBean(String url, int progress) {
        this.url = url;
        this.progress = progress;
    }

    public String url;
    public int progress;
}
