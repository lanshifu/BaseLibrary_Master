package com.lanshifu.demo_module.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.tools.avoidonresult.ActivityResultInfo;
import com.lanshifu.baselibrary.tools.avoidonresult.AvoidOnResult;
import com.lanshifu.baselibrary.utils.NetworkUtils;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.commonservice.demo.DemoInfo;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.bean.FinalClass;
import com.lanshifu.demo_module.design_mode.ProducerConsumerTest;
import com.lanshifu.demo_module.mvp.presenter.DemoMainPresenter;
import com.lanshifu.demo_module.mvp.view.DemoMainView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import me.ele.uetool.UETool;

@Route(path = RouterHub.DEMO_MAIN_ACTIVITY)
public class DemoMainActivity extends BaseTitleBarActivity<DemoMainPresenter> implements DemoMainView {

    private static final String TAG = "demomainactivity";
    @Autowired
    public String name;
    @Autowired
    int age;
    @Autowired(name = "girl") // 通过name来映射URL中的不同参数
            boolean boy;
    @Autowired
    DemoInfo mDemoInfo;    // 支持解析自定义对象，URL中使用json传递


    private H mHandler;
    private boolean mWifiProxy;


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
        mPresenter.request();

        requestPermission();

        FinalClass finalClass = new FinalClass();
        finalClass.staticName = "更改静态变量";
        LogHelper.d("staticName = " + finalClass.staticName);
        finalClass.setName("12");

        //long 越界
        long l = Long.MAX_VALUE + 1000L;
        LogHelper.d("Long.MAX_VALUE = " + Long.MAX_VALUE);
        LogHelper.d("long越界 l = " + l);

//        UETool.showUETMenu();

        handlerThreadTest();

        mWifiProxy = NetworkUtils.isWifiProxy(this);
        Log.i(TAG, "initView: ");
        Log.i(TAG, "initView: ");



        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        LogHelper.d("当前时间戳："+time);

        ProducerConsumerTest test = new ProducerConsumerTest();
        test.Test();

    }

    private void handlerThreadTest() {
        HandlerThread handlerThread = new HandlerThread("suibian");
        handlerThread.start();
        //子线程的looper，Handler将运行在子线程
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(() -> {
            try {
                Thread.sleep(1000);
                LogHelper.d("任务1执行完");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        handler.post(() -> {
            LogHelper.d("任务2执行完");
        });


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onBackClick() {
        setResult(); //
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
            , R2.id.btn_refresh_media, R2.id.btn_installed_app, R2.id.btn_crash
            , R2.id.btn_pdf_list, R2.id.btn_swipeback, R2.id.btn_leak, R2.id.btn_test
            , R2.id.btn_tablayout, R2.id.btn_plugin, R2.id.btn_guard, R2.id.btn_event
            , R2.id.btn_setting, R2.id.btn_expandable_textview, R2.id.btn_litepal
            , R2.id.btn_behavior, R2.id.btn_behavior2, R2.id.btn_bottom_sheet_behavior
            , R2.id.btn_guide, R2.id.btn_webview})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_app_info) {
            startActivity(DemoAppInfoActivity.class);
        } else if (viewId == R.id.btn_wifi_password) {
            startActivity(DemoWifiPasswordActivity.class);
        } else if (viewId == R.id.btn_sign_check) {
            startActivity(DemoSignCheckActivity.class);
        } else if (viewId == R.id.btn_refresh_media) {
            mPresenter.updateMedia();
        } else if (viewId == R.id.btn_installed_app) {
            startActivity(DemoInstalledAppListActivity.class);
        } else if (viewId == R.id.btn_crash) {
            int i = 3 / 0;
        } else if (viewId == R.id.btn_pdf_list) {
            startActivity(DemoPdfListActivity.class);
        } else if (viewId == R.id.btn_leak) {
            startActivity(DemoLeakActivity.class);
        } else if (viewId == R.id.btn_swipeback) {
            startActivity(DemoSwipeBackActivity.class);
        } else if (viewId == R.id.btn_test) {
            Intent intent = new Intent(this, DemoTestActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            new AvoidOnResult(DemoMainActivity.this)
                    .startForResult(intent)
                    .subscribe(new Consumer<ActivityResultInfo>() {
                        @Override
                        public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                            LogHelper.d(activityResultInfo.getData().getStringExtra("result"));
                            showShortToast(activityResultInfo.getData().getStringExtra("result"));
                        }
                    });

//                    .startForResult(intent
//                            , new AvoidOnResult.Callback() {
//                                @Override
//                                public void onActivityResult(int resultCode, Intent data) {
//                                    LogHelper.d(data.getStringExtra("result"));
//                                    showShortToast(data.getStringExtra("result"));
//                                }
//                            });
//            startActivity(intent);
        } else if (viewId == R.id.btn_tablayout) {
            startActivity(DemoTabActivity.class);
        } else if (viewId == R.id.btn_plugin) {
            startActivity(DemoPluginActivity.class);
        } else if (viewId == R.id.btn_guard) {
            startActivity(DemoGuardActivity.class);
        } else if (viewId == R.id.btn_event) {
            startActivity(DemoTouchEventActivity.class);
        } else if (viewId == R.id.btn_setting) {
            startActivity(DemoSettingActivity.class);
        }else if (viewId == R.id.btn_expandable_textview) {
            startActivity(DemoExpandableTextViewActivity.class);
        }else if (viewId == R.id.btn_litepal) {
            startActivity(DemoLitepalActivity.class);
        }else if (viewId == R.id.btn_behavior) {
            startActivity(DemoBehavior1Activity.class);
        }else if (viewId == R.id.btn_behavior2) {
            startActivity(DemoBehavior2Activity.class);
        }else if (viewId == R.id.btn_bottom_sheet_behavior) {
            startActivity(DemoBehavior3Activity.class);
        }else if (viewId == R.id.btn_guide) {
            startActivity(DemoGuideActivity.class);
        }else if (viewId == R.id.btn_webview) {
            startActivity(DemoWebViewActivity.class);
        }
    }


    private void requestPermission() {
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
