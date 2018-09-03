package com.lanshifu.wanandroid_module.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.fragment.BaseFragment;
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

@Route(path = RouterHub.WANDROID_MAIN_FRAGMENT)
public class WandroidMainFragment extends BaseFragment {

    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.wanandroid_main_fragment;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R2.id.btn_login, R2.id.btn_unlogin, R2.id.btn_collect})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_login) {
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
        } else if (viewId == R.id.btn_collect) {
            getCollectList();
        } else if (viewId == R.id.btn_unlogin) {
            CookieManger.clearAllCookies();
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
