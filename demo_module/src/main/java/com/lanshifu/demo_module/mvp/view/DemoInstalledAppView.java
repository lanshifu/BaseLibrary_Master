package com.lanshifu.demo_module.mvp.view;

import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.demo_module.bean.AppInfo;

import java.util.List;

public interface DemoInstalledAppView extends BaseView {

    void loadInstalledAppSuccess(List<AppInfo> appInfos);

    void loadInstalledAppError(String error);
}
