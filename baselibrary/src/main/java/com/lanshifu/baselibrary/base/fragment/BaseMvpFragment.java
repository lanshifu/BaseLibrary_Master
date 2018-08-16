package com.lanshifu.baselibrary.base.fragment;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.TUtil;

/**
 * Created by 蓝师傅 on 2017/1/2.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.onDestory();
        }
    }

    @Override
    protected  void initView(){
        initPresenter();
    }

    protected void initPresenter(){
        mPresenter = TUtil.getT(this,0);
        if(mPresenter != null){
            mPresenter.mContext = this.getActivity();
            if (bindView() == null){
                LogHelper.e(">>> bindView() return null,子类没有重写此方法,如需使用mvp,请重写bindView()方法");
            }
            mPresenter.setView(bindView());
        }
    }

    /**
     * mvp presenter 绑定view，
     * fragment子类实现 BaseView子类，
     * 然后return this
     * @return
     */
    protected abstract BaseView bindView();
}
