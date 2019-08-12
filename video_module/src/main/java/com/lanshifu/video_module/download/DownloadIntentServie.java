package com.lanshifu.video_module.download;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.DownLoadObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.progress.body.ProgressInfo;
import com.lanshifu.baselibrary.utils.StorageUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.video_module.bean.DownloadDurationBean;
import com.lanshifu.video_module.db.DownloadVideoDB;
import com.lanshifu.video_module.network.VideoApi;

import org.litepal.LitePal;

import java.io.File;

import okhttp3.ResponseBody;

public class DownloadIntentServie extends IntentService {

    String title = "";
    String url = "";
    private DownloadVideoDB mDownloadVideoDB;

    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownloadIntentServie() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        title = intent.getStringExtra(KEY_TITLE);
        url = intent.getStringExtra(KEY_URL);

        long range = 0;
        //文件名
        String path = StorageUtil.getDownloadDir() + title;
        //文件存在，则获取断点续传时请求的总长度
        File file = new File(path);
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }

        //查看数据库是否有保存
        int downloadId = -1;
        mDownloadVideoDB = LitePal.where("url like ?", url)
                .order("duration")
                .findFirst(DownloadVideoDB.class);
        if (mDownloadVideoDB != null) {
            if (mDownloadVideoDB.isDownload_success()){
                UIUtil.snackbarText("该视频已下载");
                return;
            }
            if (mDownloadVideoDB.isDownloading()){
                UIUtil.snackbarText("该视频正在下载中...");
                return;
            }
            range = mDownloadVideoDB.getDownload_size();
            LogHelper.d("range = " +range);
        } else {
            mDownloadVideoDB = new DownloadVideoDB();
            mDownloadVideoDB.setTitle(title);
            mDownloadVideoDB.setUrl(url);
            mDownloadVideoDB.setDownloading(true);
            mDownloadVideoDB.save();
            downloadId = mDownloadVideoDB.getId();
        }

        int finalId = downloadId;
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .download("bytes=" + range + totalLength, url)
                .compose(RxScheduler.io_main())
                .subscribe(new DownLoadObserver<ResponseBody>(url, path,range) {
                    @Override
                    protected void onProgress(ProgressInfo progressInfo) {
                        LogHelper.d(progressInfo.toString());

                        mDownloadVideoDB.setDownload_size(progressInfo.getCurrentbytes());
                        mDownloadVideoDB.setTotal_size(progressInfo.getContentLength());
                        mDownloadVideoDB.setDuration(progressInfo.getPercent());
                        mDownloadVideoDB.setDownloading(true);
                        mDownloadVideoDB.setDownload_pause(false);
                        mDownloadVideoDB.saveAsync();
                        //进度通知
                    }

                    @Override
                    protected void onContinueDownloadProgress(boolean downloadSuccess, int progress) {
                        RxBus.getInstance().post(RxTag.TAG_DOWNLOAD_DURAGION + url, new DownloadDurationBean(url, progress));

                    }

                    @Override
                    protected void onDownLoadSuccess() {
                        LogHelper.d("onDownLoadSuccess");
                        UIUtil.snackbarText("下载成功");
                        DownloadVideoDB downloadVideoDB = LitePal.find(DownloadVideoDB.class, finalId);
                        if (downloadVideoDB != null) {
                            downloadVideoDB.setDownload_success(true);
                            downloadVideoDB.setDownloading(false);
                            downloadVideoDB.setDownload_pause(true);
                            downloadVideoDB.save();
                        }
                    }

                    @Override
                    protected void onDownFailed(String error) {
                        LogHelper.d("onDownFailed " + error);
                        mDownloadVideoDB.setDownload_success(false);
                        mDownloadVideoDB.setDownloading(false);
                        mDownloadVideoDB.setDownload_pause(true);
                        mDownloadVideoDB.save();
                        RxBus.getInstance().post(RxTag.TAG_DOWNLOAD_ERROR +url,"");
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDownloadVideoDB != null){
            if (!mDownloadVideoDB.isDownload_success()){
                mDownloadVideoDB.setDownloading(false);
                mDownloadVideoDB.setDownload_pause(true);
                mDownloadVideoDB.save();
            }
        }
    }
}
