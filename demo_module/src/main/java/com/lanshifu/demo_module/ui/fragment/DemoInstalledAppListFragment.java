package com.lanshifu.demo_module.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.fragment.BaseListFragment;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.bean.AppInfo;
import com.lanshifu.demo_module.mvp.presenter.DemoInstalledAppPresenter;
import com.lanshifu.demo_module.mvp.view.DemoInstalledAppView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class DemoInstalledAppListFragment extends BaseListFragment<DemoInstalledAppPresenter, AppInfo>
        implements DemoInstalledAppView {
    @Override
    protected BaseView bindView() {
        return this;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.demo_item_appinfo;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, AppInfo item) {
        baseViewHolder.setImageDrawable(R.id.imgApp, item.getAppIcon());
        baseViewHolder.setText(R.id.apkName, item.getAppLabel());
        baseViewHolder.setText(R.id.pkgName, item.getPkgName());
        baseViewHolder.setText(R.id.apkSize, item.getTotalSize());
        baseViewHolder.setText(R.id.apkVersion, item.getmVersion());
        baseViewHolder.setText(R.id.sigmd5, item.getSigmd5());
        baseViewHolder.setText(R.id.first_act, item.getFirstActivityName());
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        String path = mAdapter.getItem(i).path;
        ToastUtil.showShortToast("路径是：" + path);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.loadInstallerAppInfos();
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setEnableLoadMore(false);
    }

    @Override
    public void loadInstalledAppSuccess(List<AppInfo> appInfos) {
        mAdapter.replaceData(appInfos);
        mRecyclerView.finishRefresh();
        UIUtil.snackbarText("" + appInfos.size());
    }

    @Override
    public void loadInstalledAppError(String error) {
        ToastUtil.showShortToast(error);
        mRecyclerView.finishRefresh(false);
    }

    @Override
    public void showProgressDialog(String text) {

    }

    @Override
    public void hideProgressDialog() {

    }
}
