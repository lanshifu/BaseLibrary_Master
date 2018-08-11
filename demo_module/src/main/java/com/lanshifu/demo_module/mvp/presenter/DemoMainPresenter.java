package com.lanshifu.demo_module.mvp.presenter;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.demo_module.mvp.view.DemoMainView;

public class DemoMainPresenter extends BasePresenter<DemoMainView> {

    public void test(){
        mView.textResult("哈哈哈");
    }
}
