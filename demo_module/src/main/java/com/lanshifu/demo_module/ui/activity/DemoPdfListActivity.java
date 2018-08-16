package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.bean.PdfData;
import com.lanshifu.demo_module.mvp.presenter.DemoPdfPresenter;
import com.lanshifu.demo_module.mvp.view.DemoPdfView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class DemoPdfListActivity extends BaseListTitleBarActivity<DemoPdfPresenter, PdfData>
        implements DemoPdfView {
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
        String path = mAdapter.getItem(i).path;
        ToastUtil.showShortToast(path);
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
    }

    @Override
    public void loadPdfError(String error) {
        mRecyclerView.finishRefresh(false);
    }

    @Override
    public void showProgressDialog(String text) {

    }

    @Override
    public void hideProgressDialog() {

    }

}
