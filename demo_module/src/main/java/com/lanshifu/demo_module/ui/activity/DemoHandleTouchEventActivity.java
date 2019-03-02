package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.widget.InnerRecyclerView;
import com.lanshifu.demo_module.widget.OutListScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 处理滑动冲突测试
 */
public class DemoHandleTouchEventActivity extends BaseTitleBarActivity {
    @BindView(R.id.recyclerView_outer)
    RecyclerView mRecyclerViewOuter;
    @BindView(R.id.recyclerView_inner)
    InnerRecyclerView mRecyclerViewInner;
    @BindView(R.id.scrollView_out)
    OutListScrollView mScrollViewOut;
    @BindView(R.id.scrollView_in)
    ScrollView mScrollViewIn;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_handle_touchevent_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("滑动冲突");

        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.demo_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String data) {
                baseViewHolder.setText(R.id.tv_title, data);
                baseViewHolder.setText(R.id.tv_content, data);

            }
        };
        mRecyclerViewOuter.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewInner.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewOuter.setAdapter(mAdapter);
        mRecyclerViewInner.setAdapter(mAdapter);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("数据" + i);
        }
        mAdapter.addData(list);

        //外部拦截，手动 setRecyclerView
        mScrollViewOut.setRecyclerView(mRecyclerViewOuter);



    }

}
