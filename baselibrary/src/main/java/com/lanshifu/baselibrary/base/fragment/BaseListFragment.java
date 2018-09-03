package com.lanshifu.baselibrary.base.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.utils.TUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by 蓝师傅 on 2017/1/2.
 * P 是presenter，如果不使用mvp可以传默认 BasePresenter
 * T 是数据类型 比如 UserInfo
 */

public abstract class BaseListFragment<P extends BasePresenter, T> extends BaseFragment implements
        BaseQuickAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    protected P mPresenter;
    protected CommRecyclerView mRecyclerView;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;

    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        initPresenter();
        initAdapter();
    }

    private void initPresenter() {
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
            mPresenter.setView(bindView());
        }
    }

    /**
     * 使用泛型抽取adapter
     */
    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemLayout()) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, T data) {
                convertData(baseViewHolder, data);
            }
        };

        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.autoRefresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        mRecyclerView = null;
        mAdapter = null;
    }


    /**
     * presenter 绑定view ，fragment实现BaseView 类型， 然后return this
     * 不使用mvp则返回null
     *
     * @return
     */
    protected abstract BaseView bindView();


    /**
     * item 布局
     *
     * @return
     */
    protected abstract int getItemLayout();

    /**
     * 数据更新操作
     *
     * @param baseViewHolder
     * @param data
     */
    protected abstract void convertData(BaseViewHolder baseViewHolder, T data);
}
