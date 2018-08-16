package com.lanshifu.video_module.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.commonservice.video.bean.VideoInfo;
import com.lanshifu.commonservice.video.service.VideoInfoService;

@Route(path = RouterHub.VIDEO_SERVICE_VIDEO_INFO_SERVICE, name = "91视频组件")
public class VideoInfoServiceImpl implements VideoInfoService {
    private Context mContext;
    @Override
    public VideoInfo getInfo() {
        return new VideoInfo("91视频组件");
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
