package com.lanshifu.video_module.ui.fragment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseFragment;
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
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@Route(path = RouterHub.VIDEO_MAIN_FRAGMENT)
public class VideoMainFragment extends BaseFragment<VideoMainPresenter> implements VideoMainView {

    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;
    private BaseQuickAdapter<VideoListItemBean, BaseViewHolder> mAdapter;

    private int mCurrentPage = 1;
    private int mPageCount = 20;
    private int mType = -1;

    @Override
    protected void initView() {

        initRecyclerView();

        refresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_fragment_main;
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
                String title = TextUtils.isEmpty(data.getTitle()) ? "" : data.getTitle();
                JZVideoPlayerStandard jzVideoPlayerStandard = baseViewHolder.getView(R.id.videoplayer);
                jzVideoPlayerStandard.setUp(data.getUrl(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                        title);
                Glide.with(VideoMainFragment.this)
                        .load(data.getImg())
                        .into(jzVideoPlayerStandard.thumbImageView);
//                jzVideoPlayerStandard.widthRatio = 4;//播放比例
//                jzVideoPlayerStandard.heightRatio = 3;
                baseViewHolder.getView(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDownloadDialog(data.getTitle(),data.getUrl());
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mCurrentPage++;
                mPresenter.getVideoList(mCurrentPage, mPageCount,mType);
            }
        });
        recyclerView.autoRefresh();

    }

    private void showDownloadDialog(String title, String url) {

        new AlertDialog.Builder(getActivity())
                .setMessage("下载" + title +" ?")
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitUrlManager.getInstance().setRun(false);
                        mPresenter.downLoad(title + ".mp4",url);
                    }
                }).show();
    }

    @Override
    public void getVideoListSuccess(List<VideoListItemBean> list) {
        recyclerView.finishLoadMore();
        recyclerView.finishRefresh();
        if (list.size() == 0){
            recyclerView.setNoMoreData(false);
            return;
        }
        if (mCurrentPage == 1) {
            mAdapter.replaceData(list);
        } else {
            mAdapter.addData(list);
        }

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
    public void onStop() {
        super.onStop();
        JZVideoPlayer.releaseAllVideos();
    }


    public void setType(int type){
        mType = type;
        refresh();
    }



    private void refresh(){
        RetrofitUrlManager.getInstance().setRun(true);
        recyclerView.setNoMoreData(false);
        mCurrentPage = 1;
        mPresenter.getVideoList(mCurrentPage,mPageCount,mType);
    }
}
