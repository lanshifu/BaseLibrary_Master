package com.lanshifu.video_module.ui.activity;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.R2;
import com.lanshifu.video_module.db.DownloadVideoDB;
import com.lanshifu.video_module.widget.VidwoDownLoadItemView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;

public class VideoDownLoadDetailActivity extends BaseTitleBarActivity {

    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;
    private BaseQuickAdapter<DownloadVideoDB, BaseViewHolder> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.video_download_activity;
    }

    @Override
    protected BaseView bindPresenterAndView() {
        return null;
    }

    @Override
    protected void initView(Bundle bundle) {

        setTitleText("下载任务详情");
        initRecyclerView();

        loadData();


    }



    private void loadData() {
        List<DownloadVideoDB> all = LitePal.findAll(DownloadVideoDB.class);
        mAdapter.replaceData(all);
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<DownloadVideoDB, BaseViewHolder>(R.layout.video_download_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, DownloadVideoDB data) {

                VidwoDownLoadItemView vidwoDownLoadItemView=  baseViewHolder.getView(R.id.video_item_view);
                vidwoDownLoadItemView.setTitle(data.getTitle());
                vidwoDownLoadItemView.setDuration(data.getDuration()+"");
                vidwoDownLoadItemView.setUrl(data.getUrl());
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setEnableLoadMore(false);
        recyclerView.setEnableRefresh(false);
        //
    }



}
