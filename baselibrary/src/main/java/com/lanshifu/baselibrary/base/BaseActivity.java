package com.lanshifu.baselibrary.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.utils.TUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.LoadingDialog;
import com.lanshifu.baselibrary.widget.StatusBarCompat;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 蓝师傅 on 2016/12/24.
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {

    public P mPresenter;
    public Context mContext;
    public RxManager mRxManager;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(setContentViewId());
        doAfterSetContentView();
        mUnbinder = ButterKnife.bind(this);
        mRxManager = new RxManager();
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        initPresenter();
        initView();
    }

    protected abstract int setContentViewId();

    /**
     * 设置layout前配置
     */
    protected void doBeforeSetcontentView() {

        // 把actvity放到application栈中管理
        AppManager.getInstance().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        setStatusBarColor();

    }

    protected void doAfterSetContentView() {
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void setStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void setTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void addFragment(int container, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(container, fragment);
        ft.commit();
    }


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtil.showShortToast(text);
    }

    public void showErrorToast(String text) {
        ToastUtil.showErrorToast(this, text);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtil.showLongToast(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
        mRxManager.clear();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }



    protected void initPresenter() {
    }

    protected abstract void initView();

}
