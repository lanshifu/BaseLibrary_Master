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
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.fragment.BaseListFragment;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.bean.VideoListItemBean;
import com.lanshifu.video_module.mvp.presenter.VideoMainPresenter;
import com.lanshifu.video_module.mvp.view.VideoMainView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@Route(path = RouterHub.VIDEO_MAIN_FRAGMENT)
public class VideoMainFragment extends BaseListFragment<VideoMainPresenter, VideoListItemBean> implements VideoMainView {

    private int mCurrentPage = 1;
    private int mPageCount = 20;
    private int mType = -1;
    private boolean mIsVisiable = false;

    @Override
    protected int getItemLayout() {
        return R.layout.video_video_list_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, VideoListItemBean data) {
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
                showDownloadDialog(data.getTitle(), data.getUrl());
            }
        });
    }


    @Override
    protected BaseView bindView() {
        return this;
    }

    private void showDownloadDialog(String title, String url) {

        new AlertDialog.Builder(getActivity())
                .setMessage("下载" + title + " ?")
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitUrlManager.getInstance().setRun(false);
                        mPresenter.downLoad(title + ".mp4", url);
                    }
                }).show();
    }

    @Override
    public void getVideoListSuccess(List<VideoListItemBean> list) {
        mRecyclerView.finishLoadMore(true);
        mRecyclerView.finishRefresh(true);
        if (list.size() == 0) {
            mRecyclerView.setNoMoreData(false);
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
        mRecyclerView.finishLoadMore(false);
        mRecyclerView.finishRefresh(false);
        showShortToast(error);
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


    public void setType(int type) {
        mType = type;
        refresh();
    }

    private void refresh() {
        RetrofitUrlManager.getInstance().setRun(true);
        mRecyclerView.setNoMoreData(false);
        mCurrentPage = 1;
        mPresenter.getVideoList(mCurrentPage, mPageCount, mType);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ToastUtil.showShortToast("点击 " + i);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.getVideoList(mCurrentPage, mPageCount, mType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        if (mIsVisiable){
//            //fragment可见才去刷新
//            refresh();
//        }else {
//            mRecyclerView.finishRefresh();
//        }
        refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.goOnPlayOnPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }


    //setUserVisibileHint() 来知道当前一个fragment对用户来说是隐藏还是显示，
    // 这个方法仅仅工作在FragmentPagerAdapter中，不能被使用在一个普通的activity中。
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogHelper.d("setUserVisibleHint isVisibleToUser = " + isVisibleToUser);
        //懒加载，可见的时候才去请求网络
        if (isVisibleToUser && !mIsVisiable){
            mIsVisiable = true;
            refresh();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
