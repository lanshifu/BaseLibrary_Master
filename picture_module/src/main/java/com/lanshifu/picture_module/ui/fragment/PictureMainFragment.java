package com.lanshifu.picture_module.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseFragment;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.picture_module.R;
import com.lanshifu.picture_module.R2;
import com.lanshifu.picture_module.bean.PictureListItemBean;
import com.lanshifu.picture_module.mvp.presenter.PictureMainPresenter;
import com.lanshifu.picture_module.mvp.view.PictureMainView;
import com.lanshifu.picture_module.ui.activity.PictureDetailActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

@Route(path = RouterHub.PICTURE_MAIN_FRAGMENT)
public class PictureMainFragment extends BaseFragment<PictureMainPresenter> implements PictureMainView {

    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;
    private BaseQuickAdapter<PictureListItemBean, BaseViewHolder> mAdapter;

    private int mCurrentPage = 1;
    private int mPageCount = 20;

    @Override
    protected void initView() {

        initRecyclerView();

        mPresenter.getVideoList(mCurrentPage, mPageCount);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_fragment_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<PictureListItemBean,
                BaseViewHolder>(R.layout.picture_picture_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, PictureListItemBean data) {
                ImageView imageView = baseViewHolder.getView(R.id.imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),PictureDetailActivity.class);
                        intent.putExtra("url",data.getUrl());
                        startActivity(intent);
                    }
                });

                Glide.with(PictureMainFragment.this)
                        .load(data.getUrl())
                        .into(imageView);

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
    public void getVideoListSuccess(List<PictureListItemBean> list) {
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
    public void onStop() {
        super.onStop();
    }

}
