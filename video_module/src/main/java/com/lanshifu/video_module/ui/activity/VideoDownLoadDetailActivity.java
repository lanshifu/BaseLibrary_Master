package com.lanshifu.video_module.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.utils.FileUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.db.DownloadVideoDB;
import com.lanshifu.video_module.mvp.presenter.VideoMainPresenter;
import com.lanshifu.video_module.widget.VidwoDownLoadItemView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.litepal.LitePal;

import java.util.List;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class VideoDownLoadDetailActivity extends BaseListTitleBarActivity<VideoMainPresenter,DownloadVideoDB> {

    @Override
    protected int getLayoutId() {
        return R.layout.video_download_activity;
    }

    @Override
    protected BaseView bindView() {
        return null;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.video_download_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, DownloadVideoDB data) {
        VidwoDownLoadItemView vidwoDownLoadItemView=  baseViewHolder.getView(R.id.video_item_view);
        vidwoDownLoadItemView.setData(data);
    }


    private void loadData() {
        List<DownloadVideoDB> all = LitePal.findAll(DownloadVideoDB.class);
        mAdapter.replaceData(all);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DownloadVideoDB downloadVideoDB = mAdapter.getData().get(position);
        String path = downloadVideoDB.getPath();
        if (downloadVideoDB.isDownload_success()){
            ToastUtil.showLongToast(path);
            FileUtil.playVideo(this,path);
        }else if (downloadVideoDB.isDownloading()){
            ToastUtil.showShortToast("正在下载");
        }else {
            ToastUtil.showShortToast("继续下载");
            RetrofitUrlManager.getInstance().setRun(false);
            mPresenter.downLoad(downloadVideoDB.getTitle() + ".mp4", downloadVideoDB.getUrl());


//            String fileName = downloadVideoDB.getTitle() + ".mp4";
//            mPresenter.download2(downloadVideoDB.getUrl(),StorageUtil.getDownloadDir() + fileName,downloadVideoDB.getTitle());

        }


    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        //  图库

    }

    @Override
    protected boolean isAutoRefresh() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView.setEnableLoadMore(false);
        mRecyclerView.setEnableRefresh(false);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitUrlManager.getInstance().setRun(true);
    }




}
