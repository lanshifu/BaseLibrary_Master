package com.lanshifu.video_module.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.bean.DownloadDurationBean;
import com.lanshifu.video_module.db.DownloadVideoDB;

import io.reactivex.functions.Consumer;

public class VidwoDownLoadItemView extends FrameLayout {

    private TextView mTv_title;
    private TextView mTv_duration;
    private RxManager mRxManager;
    private String mUrl;
    private DownloadVideoDB mDownloadVideoDB;

    FlikerProgressBar mProgressBar;

    public VidwoDownLoadItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VidwoDownLoadItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VidwoDownLoadItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = inflate(context, R.layout.video_download_item_layout,null);
        mTv_title = view.findViewById(R.id.tv_title);
        mTv_duration = view.findViewById(R.id.tv_duration);
        mProgressBar = view.findViewById(R.id.progress_bar);
        addView(view);

    }

    public void setData(DownloadVideoDB data){
        mDownloadVideoDB = data;

        this.setTitle(data.getTitle());
        this.setUrl(data.getUrl());
//        this.setDuration(data.getDuration()+"");

        if (data.isDownloading()){

        }else if (data.isDownload_success()){
            mProgressBar.finishLoad();
        }else if (data.isDownload_pause()){
            mProgressBar.toggle();
        }

        if (data.getTotal_size() > 0){
            mProgressBar.setProgress(data.getDownload_size() * 100  / data.getTotal_size());
        }

    }

    public void setTitle(String title){
        mTv_title.setText(title);
    }

    public void setDuration(String duration){
        mTv_duration.setText(duration);
    }

    public void setUrl(String url){
        mUrl = url;

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mRxManager == null){
            mRxManager = new RxManager();
        }

        mRxManager.on(RxTag.TAG_DOWNLOAD_DURAGION + mUrl, new Consumer<DownloadDurationBean>() {
            @Override
            public void accept(DownloadDurationBean downloadDurationBean) throws Exception {
                LogUtil.d("收到进度通知" + downloadDurationBean.progress);
                mProgressBar.setStop(false);
                mProgressBar.setProgress(downloadDurationBean.progress);
            }
        });

        mRxManager.on(RxTag.TAG_DOWNLOAD_ERROR + mUrl, new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                LogUtil.d("收到下载失败通知");
                mProgressBar.setStop(true);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRxManager.clear();
        RxBus.getInstance().unregister(RxTag.TAG_DOWNLOAD_DURAGION + mUrl);
    }
}
