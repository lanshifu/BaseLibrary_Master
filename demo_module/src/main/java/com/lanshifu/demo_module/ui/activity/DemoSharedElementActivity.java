package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.demo_module.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanshifu on 2018/11/12.
 */

public class DemoSharedElementActivity extends BaseListTitleBarActivity<BasePresenter,String> {

    public static final String EXTRA_TRANSITION = "EXTRA_TRANSITION";
    public static final String TRANSITION_FADE_FAST = "FADE_FAST";
    public static final String TRANSITION_FADE_SLOW = "FADE_SLOW";
    public static final String TRANSITION_SLIDE_RIGHT = "SLIDE_RIGHT";
    public static final String TRANSITION_SLIDE_BOTTOM = "SLIDE_BOTTOM";
    public static final String TRANSITION_EXPLODE = "EXPLODE";
    public static final String TRANSITION_EXPLODE_BOUNCE = "EXPLODE_BOUNCE";


    private List<String> mData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseView bindView() {
        return null;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.demo_list_item;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, String data) {

        View rootView = baseViewHolder.getView(R.id.ll_root);
        //设置共享元素名称
        ViewCompat.setTransitionName(rootView, data);
        // article
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("共享元素动画");
        for (int i = 0; i < 20; i++) {
            mData.add("数据" + i);
        }
        mRecyclerView.setEnableRefresh(false);
        mRecyclerView.setEnableLoadMore(false);
        mAdapter.replaceData(mData);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Pair squareParticipant = new Pair<>(view, ViewCompat.getTransitionName(view));
//        ActivityOptionsCompat transitionActivityOptions =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        DemoSharedElementActivity.this, squareParticipant);
        Intent intent = new Intent(
                DemoSharedElementActivity.this, DemoSharedElementDetailActivity.class);

        intent.putExtra(
                EXTRA_TRANSITION, TRANSITION_FADE_FAST);
        Pair participants = new Pair<>(view, ViewCompat.getTransitionName(view));
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        DemoSharedElementActivity.this, participants);
        ActivityCompat.startActivity(DemoSharedElementActivity.this,intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
