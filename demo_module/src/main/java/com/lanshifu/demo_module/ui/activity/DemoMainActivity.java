package com.lanshifu.demo_module.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.commonservice.demo.DemoInfo;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.mvp.presenter.DemoMainPresenter;
import com.lanshifu.demo_module.mvp.view.DemoMainView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;

@Route(path = RouterHub.DEMO_MAIN_ACTIVITY)
public class DemoMainActivity extends BaseTitleBarActivity<DemoMainPresenter> implements DemoMainView {

    @Autowired
    public String name;
    @Autowired
    int age;
    @Autowired(name = "girl") // 通过name来映射URL中的不同参数
            boolean boy;
    @Autowired
    DemoInfo mDemoInfo;    // 支持解析自定义对象，URL中使用json传递

    private H mHandler;


    /**
     * handler的正确用法,activity使用WeakReference引用
     */
    private static class H extends Handler {

        WeakReference<DemoMainActivity> mActivityWeakReference;

        public H(DemoMainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivityWeakReference.get() != null) {
                DemoMainActivity activity = mActivityWeakReference.get();
                LogHelper.d(activity.age + "");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.demo_main_activity;
    }

    @Override
    protected BaseView bindView() {
        return this;
    }

    @Override
    protected void initView(Bundle bundle) {

        setTitleText("demo组件");
        mHandler = new H(this);

        ARouter.getInstance().inject(this);
        StringBuilder sb = new StringBuilder();
        sb.append("name = " + name);
        sb.append(",age = " + age + "");
        sb.append(",boy = " + boy + "");

        LogHelper.d("param:" + sb.toString());

        ToastUtil.showShortToast(sb.toString());

        mPresenter.test();

        requestPermissionAndLoad();
    }

    @Override
    protected void onBackClick() {
        setResult();
        super.onBackClick();

    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra("result", "返回结果");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


    @Override
    public void showProgressDialog(String text) {

    }

    @Override
    public void hideProgressDialog() {

    }


    @Override
    public void textResult(String result) {
        ToastUtil.showShortToast(result);
    }


    @OnClick({R2.id.btn_app_info, R2.id.btn_wifi_password, R2.id.btn_sign_check
            , R2.id.btn_refresh_media, R2.id.btn_installed_app, R2.id.btn_crash, R2.id.btn_pdf_list})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_app_info) {
            startActivity(DemoAppInfoActivity.class);
        } else if (viewId == R.id.btn_wifi_password) {
            startActivity(DemoWifiPasswordActivity.class);
        } else if (viewId == R.id.btn_sign_check) {
            startActivity(DemoSignCheckActivity.class);
        } else if (viewId == R.id.btn_refresh_media) {
            //具体到某个文件的路径
            mPresenter.updateMedia();
        } else if (viewId == R.id.btn_installed_app) {
            //具体到某个文件的路径
            startActivity(DemoInstalledAppListActivity.class);
        } else if (viewId == R.id.btn_crash) {
            //具体到某个文件的路径
            int i = 3 / 0;
        } else if (viewId == R.id.btn_pdf_list) {
            startActivity(DemoPdfListActivity.class);
        }
    }



    private void requestPermissionAndLoad(){
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .compose(RxScheduler.io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        showShortToast("权限" + aBoolean);
                    }
                });
    }

}
