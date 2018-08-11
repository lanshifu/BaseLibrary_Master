package com.lanshifu.video_module.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class DownloadVideoDB extends LitePalSupport {

    int id;
    String title;
    @Column(unique = true)
    String url;
    long total_size;
    long download_size;
    boolean downloading;
    boolean download_success;
    boolean download_pause;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDownloading() {
        return downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    public boolean isDownload_success() {
        return download_success;
    }

    public void setDownload_success(boolean download_success) {
        this.download_success = download_success;
    }

    public boolean isDownload_pause() {
        return download_pause;
    }

    public void setDownload_pause(boolean download_pause) {
        this.download_pause = download_pause;
    }

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

}
