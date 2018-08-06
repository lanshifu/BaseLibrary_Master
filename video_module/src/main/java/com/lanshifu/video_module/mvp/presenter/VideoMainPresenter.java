package com.lanshifu.video_module.mvp.presenter;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.DownLoadObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.baselibrary.network.progress.body.ProgressInfo;
import com.lanshifu.baselibrary.utils.StorageUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.video_module.bean.DownloadDurationBean;
import com.lanshifu.video_module.bean.VideoListItemBean;
import com.lanshifu.video_module.db.DownloadVideo;
import com.lanshifu.video_module.mvp.view.VideoMainView;
import com.lanshifu.video_module.network.VideoApi;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import okhttp3.ResponseBody;

public class VideoMainPresenter extends BasePresenter<VideoMainView>{


    public void getVideoList(int page,int page_count,int type){
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .getVideoList(page,page_count,type)
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

    public void downLoad(String title,String url){

        DownloadVideo downloadVideo = new DownloadVideo();
        downloadVideo.setTitle(title);
        downloadVideo.setUrl(url);
        downloadVideo.setDownload(false);
        downloadVideo.save();

        String path = StorageUtil.getDownloadDir() + title;
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .download(url)
                .compose(RxScheduler.io_main())
                .subscribe(new DownLoadObserver<ResponseBody>(url,path) {
                    @Override
                    protected void onProgress(ProgressInfo progressInfo) {
                        LogHelper.d(progressInfo.toString());

                        DownloadVideo downloadVideo = new DownloadVideo();
                        downloadVideo.setDownload_size(progressInfo.getCurrentbytes());
                        downloadVideo.setTotal_size(progressInfo.getContentLength());
                        downloadVideo.setDuration(progressInfo.getPercent());
                        downloadVideo.updateAll("url like ?", url);
                        //进度通知
                        mRxManager.post(RxTag.TAG_DOWNLOAD_DURAGION + url,new DownloadDurationBean(url,progressInfo));
                    }

                    @Override
                    protected void onDownLoadSuccess() {
                        LogHelper.d("onDownLoadSuccess");
                        UIUtil.snackbarText("下载成功");

                        DownloadVideo downloadVideo = new DownloadVideo();
                        downloadVideo.updateAll("url = ?", url);
                    }

                    @Override
                    protected void onDownFailed(String error) {
                        LogHelper.d("onDownFailed " + error);
                        UIUtil.snackbarText("下载失败 " +error);
                        LitePal.deleteAll(DownloadVideo.class, "url = ?", url);
                    }
                });
    }

}
