package com.lanshifu.picture_module.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.commonservice.picture.PictureInfo;
import com.lanshifu.commonservice.picture.PictureInfoService;

@Route(path = RouterHub.PICTURE_SERVICE_PICTURE_INFO_SERVICE, name = "组件")
public class PictureInfoServiceImpl implements PictureInfoService {
    private Context mContext;

    @Override
    public PictureInfo getInfo() {
        return new PictureInfo("图片组件");
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
