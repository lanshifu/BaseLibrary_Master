package com.lanshifu.video_module.mvp.presenter;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.DownLoadObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.baselibrary.network.progress.body.ProgressInfo;
import com.lanshifu.baselibrary.utils.StorageUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.video_module.bean.DownloadDurationBean;
import com.lanshifu.video_module.bean.VideoListItemBean;
import com.lanshifu.video_module.db.DownloadVideoDB;
import com.lanshifu.video_module.download.DownloadManager;
import com.lanshifu.video_module.mvp.view.VideoMainView;
import com.lanshifu.video_module.network.VideoApi;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;

public class VideoMainPresenter extends BasePresenter<VideoMainView> {


    public void getVideoList(int page, int page_count, int type) {
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .getVideoList(page, page_count, type)
                .map(new ServerResponseFunc<List<VideoListItemBean>>())
                .compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<VideoListItemBean>>() {
                    @Override
                    public void _onNext(List<VideoListItemBean> list) {
                        mView.getVideoListSuccess(list);
                    }

                    @Override
                    public void _onError(String e) {
                        mView.getVideoListError(e);
                    }
                });
    }

    public void downLoad(String title, String url) {
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
        DownloadVideoDB downloadVideoDB = LitePal.where("url like ?", url)
                .order("duration")
                .findFirst(DownloadVideoDB.class);
        if (downloadVideoDB != null) {
            if (downloadVideoDB.isDownload_success()){
                ToastUtil.showShortToast("该视频已下载");
                return;
            }
            if (downloadVideoDB.isDownloading()){
                ToastUtil.showShortToast("该视频正在下载中");
                return;
            }
            range = downloadVideoDB.getDownload_size();
            ToastUtil.showShortToast("继续下载: " + downloadVideoDB.getDuration() + "%");
            LogHelper.d("range = " +range);
        } else {
            downloadVideoDB = new DownloadVideoDB();
            downloadVideoDB.setTitle(title);
            downloadVideoDB.setUrl(url);
            downloadVideoDB.setDownloading(true);
            downloadVideoDB.setPath(path);
            downloadVideoDB.save();
            downloadId = downloadVideoDB.getId();
            ToastUtil.showShortToast("开始下载");
        }

        DownloadVideoDB finalDownloadVideoDB = downloadVideoDB;
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .download("bytes=" + range + totalLength, url)
                .compose(RxScheduler.io_main())
                .subscribe(new DownLoadObserver<ResponseBody>(url, path,range) {
                    @Override
                    protected void onProgress(ProgressInfo progressInfo) {
                        LogHelper.d(progressInfo.toString());
                        finalDownloadVideoDB.setDownload_size(progressInfo.getCurrentbytes());
                        finalDownloadVideoDB.setTotal_size(progressInfo.getContentLength());
                        finalDownloadVideoDB.setDuration(progressInfo.getPercent());
                        finalDownloadVideoDB.setDownloading(true);
                        finalDownloadVideoDB.setDownload_pause(false);
                        finalDownloadVideoDB.save();
                        //进度通知
                    }

                    @Override
                    protected void onContinueDownloadProgress(boolean downloadSuccess, int progress) {
                        mRxManager.post(RxTag.TAG_DOWNLOAD_DURAGION + url, new DownloadDurationBean(url, progress));
                    }

                    @Override
                    protected void onDownLoadSuccess() {
                        LogHelper.d("onDownLoadSuccess");
                        UIUtil.snackbarText("下载成功");
                        if (finalDownloadVideoDB != null) {
                            finalDownloadVideoDB.setDownload_success(true);
                            finalDownloadVideoDB.setDownloading(false);
                            finalDownloadVideoDB.setDownload_pause(true);
                            finalDownloadVideoDB.save();
                        }
                        RxBus.getInstance().post(RxTag.TAG_DOWNLOAD_DURAGION + url, new DownloadDurationBean(url, 100));


                    }

                    @Override
                    protected void onDownFailed(String error) {
                        LogHelper.d("onDownFailed " + error);
                        finalDownloadVideoDB.setDownload_success(false);
                        finalDownloadVideoDB.setDownloading(false);
                        finalDownloadVideoDB.setDownload_pause(true);
                        finalDownloadVideoDB.saveAsync();
                        RxBus.getInstance().post(RxTag.TAG_DOWNLOAD_ERROR + url, "");

                    }
                });
    }



    public void download2(String url,String path,String fileName){
        DownloadManager.getImpl().startDownload(url,path,fileName);

        //插入数据库
        DownloadVideoDB downloadVideoDB = LitePal.where("url like ?", url)
                .order("duration")
                .findFirst(DownloadVideoDB.class);
        if (downloadVideoDB == null) {
            downloadVideoDB = new DownloadVideoDB();
            downloadVideoDB.setTitle(fileName);
            downloadVideoDB.setUrl(url);
            downloadVideoDB.setDownloading(true);
            downloadVideoDB.setPath(path);
            //设置下载id
            downloadVideoDB.setDownload_id(FileDownloadUtils.generateId(url, path));
            downloadVideoDB.save();
            ToastUtil.showShortToast("开始下载");
        }else {
            ToastUtil.showShortToast("继续下载");
        }

    }

}
