package com.lanshifu.video_module.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class DownloadVideo extends LitePalSupport {

    String title;
    @Column(unique = true)
    String url;
    long total_size;
    long download_size;
    boolean download;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    int duration;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotal_size() {
        return total_size;
    }

    public void setTotal_size(long total_size) {
        this.total_size = total_size;
    }

    public long getDownload_size() {
        return download_size;
    }

    public void setDownload_size(long download_size) {
        this.download_size = download_size;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }
}
