package com.lanshifu.article_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.article_module.ArticleApi;
import com.lanshifu.article_module.R;
import com.lanshifu.article_module.bean.ArticleBean;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * Created by lanshifu on 2018/11/30.
 */

public class ArticleMainActivity extends BaseListTitleBarActivity<BasePresenter, ArticleBean> {

    private int mCurrentPage = 1;
    private int mPageCount = 20;

    @Override
    protected BaseView bindView() {
        return null;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.article_list_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, ArticleBean data) {
        baseViewHolder
                .setText(R.id.tv_title, data.getTitle())
                .setText(R.id.tv_content, data.getContent());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("小说列表");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleBean articleBean = mAdapter.getData().get(position);
        LogHelper.d(articleBean.toString());
        Intent intent = new Intent(this,ArticleReaderActivity.class);
        intent.putExtra("id",articleBean.getId());
        startActivity(intent);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRecyclerView.setEnableLoadMore(true);
        mCurrentPage = 1;
        loadData();
    }


    private void loadData(){
        RetrofitHelper.getInstance().createApi(ArticleApi.class)
                .getArtileList(mCurrentPage,mPageCount)
                .map(new ServerResponseFunc<List<ArticleBean>>())
                .compose(RxScheduler.io_main_lifecycler(this))
                .subscribe(new BaseObserver<List<ArticleBean>>() {
                    @Override
                    public void _onNext(List<ArticleBean> articleBeans) {
                        mRecyclerView.finishLoadMore(true);
                        mRecyclerView.finishRefresh(true);
                        if (articleBeans.size() == 0){
                            mRecyclerView.setEnableLoadMore(false);
                            return;
                        }
                        if (mCurrentPage == 1) {
                            mAdapter.replaceData(articleBeans);
                        } else {
                            mAdapter.addData(articleBeans);
                        }

                    }

                    @Override
                    public void _onError(String e) {

                        ToastUtil.showShortToast(e);
                        mRecyclerView.finishLoadMore(true);
                        mRecyclerView.finishRefresh(true);
                    }
                });

    }
}
