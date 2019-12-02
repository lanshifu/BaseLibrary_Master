package com.lanshifu.baselibrary_master.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.fragment.BaseFragment;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.baselibrary.utils.SPUtils;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary_master.R;
import com.lanshifu.baselibrary_master.bean.PictureResp;
import com.lanshifu.baselibrary_master.bean.TempBean;
import com.lanshifu.baselibrary_master.bean.TempReq;
import com.lanshifu.baselibrary_master.network.api.DefaultApi;
import com.lanshifu.commonservice.demo.DemoInfoService;
import com.lanshifu.commonservice.picture.PictureInfoService;
import com.lanshifu.commonservice.video.service.VideoInfoService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import skin.support.SkinCompatManager;

/**
 * Created by Administrator on 2018\5\5 0005.
 */

public class MainMvpFragment extends BaseFragment {
    private static final int REQUEST_CODE_DEMO_ACTIVITY = 10;
    @BindView(R.id.btn_switch_theme)
    Button btnSwitchTheme;
    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.btn_video)
    Button btn_video;
    @BindView(R.id.btn_image)
    Button btn_image;

    @BindView(R.id.btn_demo)
    Button btn_demo;

    @Autowired(name = RouterHub.VIDEO_SERVICE_VIDEO_INFO_SERVICE)
    VideoInfoService mVideoService;

    @Autowired(name = RouterHub.PICTURE_SERVICE_PICTURE_INFO_SERVICE)
    PictureInfoService mPictureService;

    @Autowired(name = RouterHub.DEMO_SERVICE_DEMO_INFO_SERVICE)
    DemoInfoService mDemoService;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {

        //这里想展示组件向外提供服务的功能, 模拟下组件向宿主提供一些必要的信息,
        // 这里为了简单就直接返回本地数据不请求网络了
        //ARouter依赖注入
        ARouter.getInstance().inject(this);
        loadVideoInfo();
        loadPictureInfo();
        loadDemoInfo();
    }

    private void loadVideoInfo(){
        if (mVideoService != null) {
            btn_video.setText(mVideoService.getInfo().getName());
        }else {
            btn_video.setEnabled(false);
        }
    }

    private void loadPictureInfo(){
        if (mPictureService != null) {
            btn_image.setText(mPictureService.getInfo().getName());
        }else {
            btn_image.setEnabled(false);
        }
    }


    private void loadDemoInfo(){
        if (mDemoService != null) {
            btn_demo.setText(mDemoService.getInfo().getName());
        }else {
            btn_demo.setEnabled(false);
        }
    }



    @OnClick({R.id.btn_switch_theme, R.id.btn_2, R.id.btn_post, R.id.btn_get,
            R.id.btn_video, R.id.btn_image, R.id.btn_demo, R.id.btn_wandroid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_theme:
                changeTheme();
                break;

            case R.id.btn_2:
                startActivity(SecondActivity.class);
                break;

            case R.id.btn_get:
                get();
                getTempList();
                break;
            case R.id.btn_post:
                post();

                break;
            case R.id.btn_video:
                UIUtil.navigation(getActivity(), RouterHub.VIDEO_MAIN_ACTIVITY);
                break;
            case R.id.btn_image:
                UIUtil.navigation(getActivity(), RouterHub.PICTURE_MAIN_ACTIVITY);
                break;

            case R.id.btn_wandroid:
                UIUtil.navigation(getActivity(), RouterHub.WANDROID_LOGIN_ACTIVITY);
                break;

            case R.id.btn_demo:

                ARouter.getInstance().build(RouterHub.DEMO_MAIN_ACTIVITY)
                        .withString("name", "lanshifu")
                        .withInt("age", 20)
                        .withBoolean("girl", true)
//                        .withObject("key4", new Test("Jack", "Rose"))
                        .withTransition(R.anim.slide_left_in, R.anim.slide_right_out) //动画
                        .navigation(getActivity(),REQUEST_CODE_DEMO_ACTIVITY, new NavigationCallback() {

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
                break;
        }
    }

    private void changeTheme() {
        ToastUtil.showShortToast("主题更换");
        boolean dayTheme = SPUtils.getBoolean(SPUtils.KEY_THEME_DAY);
        if (dayTheme) {
            SPUtils.putBoolean(SPUtils.KEY_THEME_DAY, false);
            // 恢复应用默认皮肤
            SkinCompatManager.getInstance().restoreDefaultTheme();
        } else {
            SPUtils.putBoolean(SPUtils.KEY_THEME_DAY, true);
            // 后缀加载
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }

    }


    private void get() {
        RetrofitHelper.getInstance().createApi(DefaultApi.class)
                .getDailyList(1, 20)
                .map(new ServerResponseFunc<List<PictureResp>>())
                .compose(RxScheduler.<List<PictureResp>>io_main())
                .subscribe(new BaseObserver<List<PictureResp>>() {
                    @Override
                    public void _onNext(List<PictureResp> lsit) {

                        UIUtil.snackbarText("数据：" + lsit.size());
                        ToastUtil.showShortToast("数据：" + lsit.size());
                    }

                    @Override
                    public void _onError(String e) {
                        UIUtil.snackbarText("_onError：" + e);
                        LogUtil.e(e);
                        ToastUtil.showShortToast(e);
                    }
                });
    }

    private void post() {
        TempReq req = new TempReq();
        req.setName("lanshifu");
        req.setAddress("123456");
        String jsonData = new Gson().toJson(req);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);
        RetrofitHelper.getInstance().createApi(DefaultApi.class)
                .register(body)
                .map(new ServerResponseFunc<String>())
                .compose(RxScheduler.<String>io_main())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void _onNext(String string) {
                        UIUtil.snackbarText("数据：" + string);
                        ToastUtil.showShortToast("数据：" + string);
                    }

                    @Override
                    public void _onError(String e) {
                        UIUtil.snackbarText(e);
                        LogUtil.e(e);
                        ToastUtil.showShortToast(e);
                    }
                });
    }

    private void getTempList() {
        RetrofitHelper.getInstance().createApi(DefaultApi.class)
                .getTempList()
                .compose(RxScheduler.<TempBean>io_main())
                .subscribe(new BaseObserver<TempBean>() {
                    @Override
                    public void _onNext(TempBean pictureResp) {
                        LogUtil.d("getTempList_onNext " +pictureResp.toString());
                    }

                    @Override
                    public void _onError(String e) {
                        LogUtil.d("getTempList_onError " +e);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DEMO_ACTIVITY){
            ToastUtil.showShortToast("返回参数 "+data.getStringExtra("result"));
        }
        LogUtil.d("onActivityResult requestCode = " + requestCode);
    }
}
