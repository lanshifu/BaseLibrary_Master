package com.lanshifu.demo_module.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.commonservice.demo.DemoInfo;
import com.lanshifu.commonservice.demo.DemoInfoService;

@Route(path = RouterHub.DEMO_SERVICE_DEMO_INFO_SERVICE, name = "demo组件")
public class DemoServiceImpl implements DemoInfoService {

    private Context mContext;

    @Override
    public DemoInfo getInfo() {
        return new DemoInfo("demo组件");
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
