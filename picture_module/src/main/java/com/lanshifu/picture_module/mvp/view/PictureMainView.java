package com.lanshifu.picture_module.mvp.view;

import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.picture_module.bean.PictureListItemBean;

import java.util.List;

public interface PictureMainView extends BaseView {

    /**
     * 视频列表
     * @param list
     */
    void getVideoListSuccess(List<PictureListItemBean> list);

    void getVideoListError(String error);
}
