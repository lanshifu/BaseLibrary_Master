package com.lanshifu.video_module.mvp.view;

import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.video_module.bean.VideoListItemBean;

import java.util.List;

public interface VideoMainView extends BaseView {

    /**
     * 视频列表
     * @param list
     */
    void getVideoListSuccess(List<VideoListItemBean> list);

    void getVideoListError(String error);
}
