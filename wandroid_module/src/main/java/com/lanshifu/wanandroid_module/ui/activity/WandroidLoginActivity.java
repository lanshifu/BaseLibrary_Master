package com.lanshifu.wanandroid_module.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.cookies.CookieManger;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.wanandroid_module.R;
import com.lanshifu.wanandroid_module.R2;
import com.lanshifu.wanandroid_module.bean.WandroidResult;
import com.lanshifu.wanandroid_module.network.WandroidApi;

import butterknife.BindView;
import butterknife.OnClick;

public class WandroidLoginActivity extends BaseTitleBarActivity {
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.wanandroid_login_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("玩安卓登录");
    }


    @OnClick({R2.id.btn_login, R2.id.btn_unlogin, R2.id.btn_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                RetrofitHelper.getInstance().createApi(WandroidApi.class)
                        .login(etUsername.getText().toString(), etPassword.getText().toString())
                        .compose(RxScheduler.io_main())
                        .subscribe(new BaseObserver<WandroidResult>() {
                            @Override
                            public void _onNext(WandroidResult result) {
                                ToastUtil.showShortToast(result.toString());
                            }

                            @Override
                            public void _onError(String e) {
                                ToastUtil.showShortToast(e);
                            }
                        });
                break;
            case R.id.btn_collect:
                getCollectList();
                break;

            case R.id.btn_unlogin:
                CookieManger.clearAllCookies();
                break;
        }
    }

    private void getCollectList() {
        RetrofitHelper.getInstance().createApi(WandroidApi.class)
                .collectList(0)
                .compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<WandroidResult>() {
                    @Override
                    public void _onNext(WandroidResult result) {
                        ToastUtil.showShortToast(result.toString());
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast(e);
                    }
                });
    }
}
