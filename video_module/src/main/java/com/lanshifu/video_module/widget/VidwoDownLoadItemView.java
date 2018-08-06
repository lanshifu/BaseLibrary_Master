package com.lanshifu.video_module.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.bean.DownloadDurationBean;

import io.reactivex.functions.Consumer;

public class VidwoDownLoadItemView extends FrameLayout {

    private TextView mTv_title;
    private TextView mTv_duration;
    private RxManager mRxManager;
    private String mUrl;

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
        addView(view);

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
        mRxManager = new RxManager();
        mRxManager.on(RxTag.TAG_DOWNLOAD_DURAGION + mUrl, new Consumer<DownloadDurationBean>() {
            @Override
            public void accept(DownloadDurationBean downloadDurationBean) throws Exception {
                mTv_duration.setText(downloadDurationBean.progressInfo.getPercent() + "%");
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRxManager = null;
    }
}
