package com.lanshifu.demo_module.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.demo_module.bean.AppInfo;
import com.lanshifu.demo_module.mvp.view.DemoInstalledAppView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class DemoInstalledAppPresenter extends BasePresenter<DemoInstalledAppView> {

    public void loadInstallerAppInfos() {

        Observable.create((ObservableOnSubscribe<List<AppInfo>>) emitter -> {

            @SuppressLint("WrongConstant")
            List<ApplicationInfo> apps = mContext.getPackageManager().getInstalledApplications(
                    PackageManager.GET_SIGNATURES);
            ArrayList<AppInfo> infos = new ArrayList<AppInfo>();

            for (ApplicationInfo info : apps) {
                if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    // 非系统应用
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppLabel(info.loadLabel(mContext.getPackageManager()).toString());
                    appInfo.setAppIcon(info.loadIcon(mContext.getPackageManager()));
                    appInfo.path = info.sourceDir;
                    appInfo.setPkgName(info.packageName);
                    infos.add(appInfo);
                }
            }

            emitter.onNext(infos);
            emitter.onComplete();
        }).compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<AppInfo>>() {
                    @Override
                    public void _onNext(List<AppInfo> appInfos) {
                        mView.loadInstalledAppSuccess(appInfos);
                    }

                    @Override
                    public void _onError(String e) {
                        mView.loadInstalledAppError(e);
                    }
                });
    }
}
