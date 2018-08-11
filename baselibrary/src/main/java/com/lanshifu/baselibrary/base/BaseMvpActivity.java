package com.lanshifu.baselibrary.base;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.utils.TUtil;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    public P mPresenter;

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
    }

    /**
     * presenter和view绑定，手动调用如下：
     * mPresenter.setView（this）
     */
    protected void initPresenter(){
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
            mPresenter.setView(bindPresenterAndView());
        }
    }

    protected abstract BaseView bindPresenterAndView();
}
