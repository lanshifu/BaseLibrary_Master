package com.lanshifu.demo_module.ui.activity;

import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.notification.NotifyManager;
import com.lanshifu.baselibrary.tools.avoidonresult.ActivityResultInfo;
import com.lanshifu.baselibrary.tools.avoidonresult.AvoidOnResult;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.VersionAdapterUtil;
import com.lanshifu.commonservice.demo.DemoInfo;
import com.lanshifu.demo_module.DemoApplication;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.appupdate.AppUpdateManager;
import com.lanshifu.demo_module.bean.FinalClass;
import com.lanshifu.demo_module.design_mode.ProducerConsumerTest;
import com.lanshifu.demo_module.mvp.presenter.DemoMainPresenter;
import com.lanshifu.demo_module.mvp.view.DemoMainView;
import com.lanshifu.demo_module.ndk.JniTest;
import com.lanshifu.demo_module.util.ChannelUtil;
import com.lanshifu.demo_module.xxpermission.XXPermissions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;

@Route(path = RouterHub.DEMO_MAIN_ACTIVITY)
public class DemoMainActivity extends BaseTitleBarActivity<DemoMainPresenter> implements DemoMainView {

    private static final String TAG = "demomainactivity";
    private static final int REQUEST_CODE_MAP_ACTIVITY = 10;
    private static final int REQUEST_CODE_UNKNOW_APP = 11;
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

        private H(DemoMainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivityWeakReference.get() != null) {
                DemoMainActivity activity = mActivityWeakReference.get();
                LogUtil.d(activity.age + "");
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

        LogUtil.d("进入MainActivity initView 耗时时间：" + (System.currentTimeMillis() - DemoApplication.applicationOncreateTIme));

        ImageView imageView = new ImageView(this);
        ViewTarget<ImageView, Drawable> into = Glide.with(this).load("").listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);


        setTitleText("demo组件");
        mHandler = new H(this);
        mHandler.sendEmptyMessage(1);

        ARouter.getInstance().inject(this);
        StringBuilder sb = new StringBuilder();
        sb.append("name = " + name);
        sb.append(",age = " + age + "");
        sb.append(",boy = " + boy + "");

        LogUtil.d("param:" + sb.toString());

        ToastUtil.showShortToast(sb.toString());

        mPresenter.test();
        mPresenter.request();

//        requestPermission();

        FinalClass finalClass = new FinalClass();
        finalClass.staticName = "更改静态变量";
        LogUtil.d("staticName == " + finalClass.staticName);
        finalClass.setName("12");

        //long 越界
        long l = Long.MAX_VALUE + 1000L;
        LogUtil.d("Long.MAX_VALUE = " + Long.MAX_VALUE);
        LogUtil.d("long越界 long = " + l);

//        UETool.showUETMenu();

//        handlerThreadTest();

//        mWifiProxy = NetworkUtils.isWifiProxy(this);
        Log.i(TAG, "initView: ");


        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        LogUtil.d("当前时间戳是：" + time);

        ProducerConsumerTest test = new ProducerConsumerTest();
        test.Test();

//        mPresenter.test_thread();

        mPresenter.rxjavaTest();

    }

    private void handlerThreadTest() {
        HandlerThread handlerThread = new HandlerThread("suibian");
        handlerThread.start();
        //子线程的looper，Handler将运行在子线程
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(() -> {
            try {
                Thread.sleep(1000);
                LogUtil.d("任务1执行完");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        handler.post(() -> {
            LogUtil.d("任务2执行完");
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
        LogUtil.d("onDestroy");
        LogUtil.closeLog();
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
            , R2.id.btn_guide, R2.id.btn_webview, R2.id.btn_shared_element, R2.id.btn_map
            , R2.id.btn_anim, R2.id.btn_ndk, R2.id.btn_unknow_resource, R2.id.btn_notify1
            , R2.id.btn_notify2, R2.id.btn_get_config, R2.id.btn_block_canary, R2.id.btn_check_update
            , R2.id.btn_SnapHelper, R2.id.btn_sd_search, R2.id.btn_test_io, R2.id.btn_event_fit
            , R2.id.btn_rxjava2, R2.id.btn_hongyang_opne_api, R2.id.btn_flutter, R2.id.btn_camera2
            , R2.id.btn_uid, R2.id.btn_picture_select, R2.id.btn_xml2code, R2.id.btn_channel
            , R2.id.btn_xxpermission, R2.id.btn_appupdate})
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
            testCrash(123);
        } else if (viewId == R.id.btn_pdf_list) {
            startActivity(DemoPdfListActivity.class);
        } else if (viewId == R.id.btn_leak) {
            startActivity(DemoLeakActivity.class);
        } else if (viewId == R.id.btn_swipeback) {
            startActivity(DemoSlideBackActivity.class);
        } else if (viewId == R.id.btn_test) {
            Intent intent = new Intent(this, DemoTestActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            new AvoidOnResult(DemoMainActivity.this)
                    .startForResult(intent)
                    .subscribe(new Consumer<ActivityResultInfo>() {
                        @Override
                        public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                            LogUtil.d(activityResultInfo.getData().getStringExtra("result"));
                            showShortToast(activityResultInfo.getData().getStringExtra("result"));
                        }
                    });
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
        } else if (viewId == R.id.btn_expandable_textview) {
            startActivity(DemoExpandableTextViewActivity.class);
        } else if (viewId == R.id.btn_litepal) {
            startActivity(DemoLitepalActivity.class);
        } else if (viewId == R.id.btn_behavior) {
            startActivity(DemoBehavior1Activity.class);
        } else if (viewId == R.id.btn_behavior2) {
            startActivity(DemoBehavior2Activity.class);
        } else if (viewId == R.id.btn_bottom_sheet_behavior) {
            startActivity(DemoBehavior3Activity.class);
        } else if (viewId == R.id.btn_guide) {
            startActivity(DemoGuideActivity.class);
        } else if (viewId == R.id.btn_webview) {
            startActivity(DemoWebViewActivity.class);
        } else if (viewId == R.id.btn_shared_element) {
            startActivity(DemoSharedElementActivity.class);
//            startZhifubao();
        } else if (viewId == R.id.btn_map) {
//            try {
//                returnUntilThreadFinish();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            toMapActivity();
        } else if (viewId == R.id.btn_anim) {
            startActivity(DemoAnimateActivity.class);
        } else if (viewId == R.id.btn_ndk) {
            showShortToast(JniTest.get());
        } else if (viewId == R.id.btn_unknow_resource) {
            boolean open = VersionAdapterUtil.checkUnknowAppAndroidO(DemoMainActivity.this);
            if (!open) {
                VersionAdapterUtil.gotoOpenUnknowApp(DemoMainActivity.this, REQUEST_CODE_UNKNOW_APP);
            } else {
                showShortToast("已打开，执行安装app操作");
            }
        } else if (viewId == R.id.btn_notify1) {
            NotifyManager.getInstance(DemoMainActivity.this)
                    .showNormalNotify(DemoMainActivity.this, "普通通知", "content",
                            new Intent(DemoMainActivity.this, DemoMainActivity.class));
        } else if (viewId == R.id.btn_notify2) {
            NotifyManager.getInstance(DemoMainActivity.this)
                    .showOtherNotify(DemoMainActivity.this, "即时消息通知", "content2",
                            new Intent(DemoMainActivity.this, DemoMainActivity.class));
        } else if (viewId == R.id.btn_get_config) {
            mPresenter.initConfig();
        } else if (viewId == R.id.btn_block_canary) {
            mPresenter.blockCanaryTest();
            mPresenter.blockCanaryTest2();
            mPresenter.blockCanaryTest3();
        } else if (viewId == R.id.btn_check_update) {
//            Beta.checkUpgrade(false,false);

            ToastUtil.showSuccessToast(this, "成功谈通知", "内容");
        } else if (viewId == R.id.btn_SnapHelper) {
            startActivity(SnapHelperTestActivity.class);
        } else if (viewId == R.id.btn_sd_search) {
            startActivity(DemoSdCardFindActivity.class);
        } else if (viewId == R.id.btn_test_io) {
//            startActivity(DemoTestIOActivity.class);
        } else if (viewId == R.id.btn_event_fit) {
            startActivity(DemoHandleTouchEventActivity.class);
        } else if (viewId == R.id.btn_rxjava2) {
            startActivity(DemoRxjavaActivity.class);
        } else if (viewId == R.id.btn_hongyang_opne_api) {
            startActivity(DemoHongyangOpenApiActivity.class);
        } else if (viewId == R.id.btn_flutter) {
            Intent intent = new Intent();
            intent.setClass(this, DemoFlutterActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.btn_camera2) {
            startActivity(DemoCamera2Activity.class);
        } else if (viewId == R.id.btn_uid) {
            startActivity(DemoHookTestActivity.class);
        } else if (viewId == R.id.btn_picture_select) {
            startActivity(PictureSelectActivity.class);
        } else if (viewId == R.id.btn_xml2code) {
            startActivity(XML2CodeActivity.class);
        }else if (viewId == R.id.btn_channel) {
            String channel = ChannelUtil.getChannel(DemoMainActivity.this);
            ToastUtil.showShortToast("渠道号："+channel);
        }else if (viewId == R.id.btn_xxpermission) {
            Intent intent = new Intent();
            intent.setClass(this, DemoXXPermissionActivity.class);
            startActivity(intent);
        }else if (viewId == R.id.btn_appupdate) {
            appUpdate();
        }
    }

    private static String[] arrayContent = new String[]{"1、实现apkUrl形式的版本更新功能", "2、实现stream形式的版本更新功能", "3、优化用户体验", "4、修复一些bug", "1、实现apkUrl形式的版本更新功能", "2、实现stream形式的版本更新功能", "3、优化用户体验", "4、修复一些bug", "1、实现apkUrl形式的版本更新功能", "2、实现stream形式的版本更新功能", "3、优化用户体验", "4、修复一些bug", "1、实现apkUrl形式的版本更新功能", "2、实现stream形式的版本更新功能", "3、优化用户体验", "4、修复一些bug"};

    private void appUpdate(){
        AppUpdateManager.Builder builder = new AppUpdateManager.Builder(DemoMainActivity.this);
        //TODO github上的文件下载极慢（甚至连接失败），测试时可以更换为自己服务器上的文件链接
//        builder.apkUrl("https://github.com/ZuoHailong/AppUpdate/blob/master/example/file/appupdate_example.apk")
        builder.apkUrl("https://drumbeat-update-app.oss-cn-hangzhou.aliyuncs.com/SupplyChain/temp/appVivoMorocco-release.apk")
//                .newVerName("2.2.2")
                .updateForce(false)
                .updateContent(arrayContent)
                .build();
    }

    private void testCrash(int i) {
//        int i = 3 / 0;
        ToastUtil.showShortToast("bug已经被修复" + i);
    }

    private void requestPermission() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        if (!aBoolean) {
                            Intent intent = getAppDetailSettingIntent(DemoMainActivity.this);
                            startActivity(intent);
                            showLongToast("请打钩相关权限才能正常使用");
                        }
                    }
                });
    }

    /**
     * 跳转到应用详情页面,权限被拒绝时调用
     *
     * @param context
     * @return
     */
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return localIntent;
    }

    public void startZhifubao() {
        // Dalvik
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        ComponentName cn = new ComponentName("com.eg.android.AlipayGphone", "com.eg.android.AlipayGphone.AlipayLogin");

        intent.setComponent(cn);
        startActivity(intent);
    }

    /**
     * 跳转去地图
     */
    private void toMapActivity() {
        ARouter.getInstance().build(RouterHub.MAP_MAIN_ACTIVITY)
                .withBoolean("mSendLocation", true)
                .withTransition(R.anim.slide_left_in, R.anim.slide_right_out) //动画
                .navigation(this, REQUEST_CODE_MAP_ACTIVITY, new NavigationCallback() {

                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtil.d("onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        LogUtil.d("onLost");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtil.d("onArrival");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        LogUtil.d("onInterrupt");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAP_ACTIVITY) {
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String address = data.getStringExtra("address");
                ToastUtil.showLongToast("latitude=" + latitude + ",longitude=" + longitude + ",address =" + address);
            } else if (requestCode == REQUEST_CODE_UNKNOW_APP) {
                showShortToast("已打开，执行安装app操作");
            }
        }
    }

    /**
     * 等待线程执行完返回，会阻塞
     *
     * @return
     */
    private int returnUntilThreadFinish() throws Exception {
        LogUtil.d("等待子线程执行完...");
        Thread.sleep(4000);
        int count = 10;
        //这是一个计时器，只有countDown 到0的时候才会走 await 之后的代码，会阻塞
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        MainApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                    LogUtil.d("子线程执行完。");

                }
            }
        });

        //等待countDown 到0的时候才继续下一步
        countDownLatch.await();
        return 10;
    }

    private void test() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    //
                }
            }
        };

        Message message = Message.obtain();
        message.what = 1;

        //1
        handler.sendMessage(message);
        //2
        handler.sendMessageDelayed(message, 1000);
        //3
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    private void test2() {
        Application application = getApplication();
        Context applicationContext = getApplicationContext();
    }


    /**
     * 获取渠道信息
     */
    private String getChannel() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("key"); // key为<meta-data>标签中的name
            if (!TextUtils.isEmpty(channel)) {
                return channel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
