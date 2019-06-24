package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.bean.PdfData;
import com.lanshifu.demo_module.mvp.presenter.DemoPdfPresenter;
import com.lanshifu.demo_module.mvp.view.DemoPdfView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class DemoPdfListActivity extends BaseListTitleBarActivity<DemoPdfPresenter, PdfData>
        implements DemoPdfView {

    private RecyclerViewSkeletonScreen mSkeletonScreen;

    @Override
    protected BaseView bindView() {
        return this;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.demo_pdf_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, PdfData data) {
        baseViewHolder
                .setText(R.id.tv_title, data.name)
                .setText(R.id.tv_size, data.size + "");

    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        String path = mAdapter.getData().get(i).path;
        String name = mAdapter.getData().get(i).name;
        LogHelper.d("path =" + path);
        Intent intent = new Intent(this,DemoFileDisplayActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView.setEnableLoadMore(false);
        setTitleText("pdf列表");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.loadPdfFile();
    }

    @Override
    public void loadPdfSuccess(List<PdfData> datas) {
        mRecyclerView.finishRefresh();
        mAdapter.replaceData(datas);
        mSkeletonScreen.hide();
    }

    @Override
    public void loadPdfError(String error) {
        mRecyclerView.finishRefresh(false);
        mSkeletonScreen.hide();
    }

    @Override
    public void showProgressDialog(String text) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    protected void setAdapter(RecyclerView.Adapter adapter) {
//        super.setAdapter(adapter);
        mSkeletonScreen = Skeleton.bind(mRecyclerView.getRecyclerView())
                                .adapter(mAdapter)
                                .load(R.layout.demo_list_item_loading)
                                .shimmer(true)      // whether show shimmer animation.                      default is true
                                .count(10)          // the recycler view item count.                        default is 10
                                .color(R.color.shimmer_color)       // the shimmer color.                                   default is #a2878787
                                .angle(20)          // the shimmer angle.                                   default is 20;
                                .duration(1000)     // the shimmer animation duration.                      default is 1000;
                                .frozen(false)
                                .show();
    }
}
