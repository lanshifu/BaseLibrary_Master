package com.lanshifu.baselibrary.base.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
 * P 是presenter，如果不使用mvp可以传默认 BasePresenter
 * T 是数据类型 比如 UserInfo
 */

public abstract class BaseListTitleBarActivity<P extends BasePresenter, T> extends BaseActivity
        implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    public P mPresenter;
    private Menu mTBMenu;
    private TextView mToolBarTitle;
    private Toolbar mToolbar;
    private ImageView mIvBack;
    protected CommRecyclerView mRecyclerView;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;

    @Override
    protected int setContentViewId() {
        return R.layout.base_activity;
    }

    protected void doAfterSetContentView() {
        mToolBarTitle = (TextView) findViewById(R.id.comm_toolbar_title);// 自定义的标题TextView
        // 自定义的标题TextView
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mToolbar = (Toolbar) findViewById(R.id.comm_toolbar);
        FrameLayout container = (FrameLayout) findViewById(R.id.comm_container);
        initContainer(container);
        initAdapter();
        initPresenter();
        if (onIfShowTB()) {
            mToolbar.setVisibility(View.VISIBLE);
            mToolBarTitle.setVisibility(View.VISIBLE);
            initToolBar(mToolbar);
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
            mToolBarTitle.setVisibility(View.GONE);
        }

    }

    private void initContainer(FrameLayout container) {
        View view = getLayoutInflater().inflate(R.layout.layout_comm_recyclerview, null);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        container.addView(view);
    }

    // ====== ToolBar或者ActionBar的初始化 ======
    private void initToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 为了能够调整标题居中，隐藏actionbar自带的标题，用自己定义的标题TextView
            actionBar.setDisplayShowTitleEnabled(false);
            // 设置显示返回键
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void onBackClick() {
        finish();
    }

    /**
     * 设置标题栏的名字
     *
     * @param title 标题栏的名字
     */
    protected void setTitleText(CharSequence title) {
        mToolBarTitle.setText(title);
    }

    protected void showBackIcon() {
        mIvBack.setVisibility(View.VISIBLE);
    }

    protected void hideBackIcon() {
        mIvBack.setVisibility(View.GONE);
    }

    /**
     * 获取返回控件，可以设置别的图标
     *
     * @return
     */
    protected ImageView getBackIconView() {
        return mIvBack;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mTBMenu = menu;
        if (getTBMenusId() > 0) {
            getMenuInflater().inflate(getTBMenusId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 设置右上角菜单,返回菜单资源id
     */
    protected int getTBMenusId() {
        return -1;
    }

    /**
     * 隐藏某个菜单
     *
     * @param menuId
     */
    protected void hideTBMenuItem(int menuId) {
        MenuItem tbMenuItem = getTBMenuItem(menuId);
        tbMenuItem.setVisible(false);
    }

    protected void showTBMenuItem(int menuId) {
        MenuItem tbMenuItem = getTBMenuItem(menuId);
        tbMenuItem.setVisible(true);
    }

    protected MenuItem getTBMenuItem(int menuItemId) {
        return mTBMenu != null ? mTBMenu.findItem(menuItemId) : null;
    }

    /**
     * 是否显示toolbar
     *
     * @return
     */
    protected boolean onIfShowTB() {
        return true;
    }

    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
    }

    protected void initPresenter() {
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
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
        if (isAutoRefresh()) {
            mRecyclerView.autoRefresh();
        }
    }

    /**
     * mvp的 presenter 和view 绑定
     *
     * @return this
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

    protected boolean isAutoRefresh(){
        return true;
    }
}
