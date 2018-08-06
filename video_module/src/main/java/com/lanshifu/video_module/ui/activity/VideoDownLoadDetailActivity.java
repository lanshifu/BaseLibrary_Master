package com.lanshifu.video_module.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.R2;
import com.lanshifu.video_module.bean.DownloadDurationBean;
import com.lanshifu.video_module.db.DownloadVideo;
import com.lanshifu.video_module.widget.VidwoDownLoadItemView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class VideoDownLoadDetailActivity extends BaseTitleBarActivity {

    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;
    private BaseQuickAdapter<DownloadVideo, BaseViewHolder> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.video_download_activity;
    }

    @Override
    protected void initView() {

        setTitleText("下载任务详情");
        initRecyclerView();

        loadData();

        initRxBus();

    }

    private void initRxBus() {
        mRxManager.on(RxTag.TAG_DOWNLOAD_DURAGION, new Consumer<DownloadDurationBean>() {
            @Override
            public void accept(DownloadDurationBean downloadDurationBean) throws Exception {
                LogHelper.d("收到进度通知 ："+downloadDurationBean.progressInfo.getPercent());
            }
        });
    }

    private void loadData() {
        List<DownloadVideo> all = LitePal.findAll(DownloadVideo.class);
        mAdapter.replaceData(all);
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<DownloadVideo, BaseViewHolder>(R.layout.video_download_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, DownloadVideo data) {

                VidwoDownLoadItemView vidwoDownLoadItemView=  baseViewHolder.getView(R.id.video_item_view);
                vidwoDownLoadItemView.setTitle(data.getTitle());
                vidwoDownLoadItemView.setDuration(data.getDuration()+"");
                vidwoDownLoadItemView.setUrl(data.getUrl());
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setEnableLoadMore(false);
        recyclerView.setEnableRefresh(false);
    }



}
