package com.lanshifu.video_module.ui;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.R2;
import com.lanshifu.video_module.bean.VideoListItemBean;
import com.lanshifu.video_module.mvp.presenter.VideoMainPresenter;
import com.lanshifu.video_module.mvp.view.VideoMainView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

@Route(path = RouterHub.VIDEO_MAIN_ACTIVITY)
public class VideoMainActivity extends BaseTitleBarActivity<VideoMainPresenter> implements VideoMainView {

    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;
    private BaseQuickAdapter<VideoListItemBean, BaseViewHolder> mAdapter;

    private int mCurrentPage = 1;
    private int mPageCount = 20;

    @Override
    protected void initView() {

        setTitleText("91视频");
        initRecyclerView();

        mPresenter.getVideoList(mCurrentPage, mPageCount);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<VideoListItemBean,
                BaseViewHolder>(R.layout.video_video_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, VideoListItemBean data) {
                JZVideoPlayerStandard jzVideoPlayerStandard = baseViewHolder.getView(R.id.videoplayer);
                jzVideoPlayerStandard.setUp(data.getUrl(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                        data.getTitle());
                Glide.with(VideoMainActivity.this)
                        .load(data.getImg())
                        .into(jzVideoPlayerStandard.thumbImageView);
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mCurrentPage = 1;
                mPresenter.getVideoList(mCurrentPage, mPageCount);
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mCurrentPage++;
                mPresenter.getVideoList(mCurrentPage, mPageCount);
            }
        });
        recyclerView.autoRefresh();

    }

    @Override
    public void getVideoListSuccess(List<VideoListItemBean> list) {
        if (mCurrentPage == 1) {
            mAdapter.replaceData(list);
        } else {
            mAdapter.addData(list);
        }
        recyclerView.finishLoadMore();
        recyclerView.finishRefresh();
    }

    @Override
    public void getVideoListError(String error) {
        recyclerView.finishLoadMore();
        recyclerView.finishRefresh();
        UIUtil.snackbarText(error);
    }

    @Override
    public void showProgressDialog(String text) {
        startProgressDialog();

    }

    @Override
    public void hideProgressDialog() {
        stopProgressDialog();
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
