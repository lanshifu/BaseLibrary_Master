package com.lanshifu.picture_module.ui.fragment;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.fragment.BaseListFragment;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.picture_module.R;
import com.lanshifu.picture_module.bean.PictureListItemBean;
import com.lanshifu.picture_module.mvp.presenter.PictureMainPresenter;
import com.lanshifu.picture_module.mvp.view.PictureMainView;
import com.lanshifu.picture_module.ui.activity.PictureDetailActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

@Route(path = RouterHub.PICTURE_MAIN_FRAGMENT)
public class PictureMainFragment extends BaseListFragment<PictureMainPresenter, PictureListItemBean>
        implements PictureMainView {

    private int mCurrentPage = 1;
    private int mPageCount = 20;

    @Override
    protected int getItemLayout() {
        return R.layout.picture_picture_list_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, PictureListItemBean data) {

        ImageView imageView = baseViewHolder.getView(R.id.imageView);
        Glide.with(PictureMainFragment.this)
                .load(data.getUrl())
                .into(imageView);
        //设置共享元素名称
        ViewCompat.setTransitionName(imageView, data.getUrl());
    }

    @Override
    protected BaseView bindView() {
        return this;
    }

    @Override
    public void getVideoListSuccess(List<PictureListItemBean> list) {
        if (mCurrentPage == 1) {
            mAdapter.replaceData(list);
        } else {
            mAdapter.addData(list);
        }
        mRecyclerView.finishLoadMore(true);
        mRecyclerView.finishRefresh(true);
    }

    @Override
    public void getVideoListError(String error) {
        mRecyclerView.finishLoadMore(false);
        mRecyclerView.finishRefresh(false);
        ToastUtil.showShortToast(error);
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
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

        //动画
        String transitionName = ViewCompat.getTransitionName(view.findViewById(R.id.imageView));
        LogUtil.d("transitionName == null?" + (transitionName == null));
        Pair squareParticipant = new Pair<>(view, transitionName);
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), squareParticipant);

        Intent intent = new Intent(getActivity(), PictureDetailActivity.class);
        intent.putExtra("url", mAdapter.getItem(i).getUrl());
        startActivity(intent,transitionActivityOptions.toBundle());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.getVideoList(mCurrentPage, mPageCount);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mPresenter.getVideoList(mCurrentPage, mPageCount);
    }
}
