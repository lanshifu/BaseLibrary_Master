package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.demo_module.R;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by lanshifu on 2018/9/17.
 */

public class Demo2Activity extends BaseTitleBarActivity {

    public static final int INT = 1;
    private int b;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_main_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        


        setTitleText(R.string.app_name);


        int i = INT;
        b = 2;

        String[] arr = new String[]{"1","2","3"};
        Observable.fromArray(arr)
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void _onNext(String s) {
                        LogHelper.d("onNext "+s);
                    }

                    @Override
                    public void _onError(String e) {
                        LogHelper.d("_onError "+e);
                    }
                });


    }
}
