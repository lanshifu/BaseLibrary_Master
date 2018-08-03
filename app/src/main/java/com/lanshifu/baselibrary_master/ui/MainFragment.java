package com.lanshifu.baselibrary_master.ui;

import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.google.gson.Gson;
import com.lanshifu.baselibrary.base.BaseFragment;
import com.lanshifu.baselibrary.log.LogHelper;
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
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.commonservice.video.service.VideoInfoService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018\5\5 0005.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.btn_switch_theme)
    Button btnSwitchTheme;
    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.btn_video)
    Button btn_video;

    @Autowired(name = RouterHub.VIDEO_SERVICE_VIDEO_INFO_SERVICE)
    VideoInfoService mVideoService;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {


        if (mVideoService != null) {
            btn_video.setText(mVideoService.getInfo().getName());
        }else {
            btn_video.setText("91视频组件信息获取失败");
        }

    }

    @OnClick({R.id.btn_switch_theme, R.id.btn_2, R.id.btn_post, R.id.btn_get, R.id.btn_video})
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
        }
    }

    private void changeTheme() {
        ToastUtil.showShortToast("主题更换");
        boolean dayTheme = SPUtils.getBoolean(SPUtils.KEY_THEME_DAY);
        if (dayTheme) {
            SPUtils.putBoolean(SPUtils.KEY_THEME_DAY, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            SPUtils.putBoolean(SPUtils.KEY_THEME_DAY, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
//        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//        getActivity().recreate();
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
                    }

                    @Override
                    public void _onError(String e) {
                        UIUtil.snackbarText("_onError：" + e);
                        LogHelper.e(e);
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
                    }

                    @Override
                    public void _onError(String e) {
                        UIUtil.snackbarText(e);
                        LogHelper.e(e);
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

                    }

                    @Override
                    public void _onError(String e) {

                    }
                });
    }
}
