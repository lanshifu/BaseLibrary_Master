package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.bean.CityResp;
import com.lanshifu.demo_module.bean.HongYangHttpResult;
import com.lanshifu.demo_module.bean.IpResp;
import com.lanshifu.demo_module.bean.WeatherResp;
import com.lanshifu.demo_module.network.DemoApi;
import com.lanshifu.demo_module.network.HongYangOpenApiRespFun;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Function;

public class DemoHongyangOpenApiActivity extends BaseActivity {
    @BindView(R.id.btn_query_city)
    Button mBtnQueryCity;
    @BindView(R.id.btn_query_ip)
    Button mBtnQueryIp;
    @BindView(R.id.tv_city)
    EditText mTvCity;
    @BindView(R.id.btn_query_weather)
    Button mBtnQueryWeather;

    @Override
    protected int setContentViewId() {
        return R.layout.demo_hongyang_openapi_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.btn_query_city, R.id.btn_query_ip, R.id.btn_query_weather})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_city:
                queryCity();
                break;
            case R.id.btn_query_ip:
                queryIp();
                break;
            case R.id.btn_query_weather:
                queryWeather();
                break;
        }
    }

    private void queryWeather() {
        String city = mTvCity.getText().toString();
        if (TextUtils.isEmpty(city)){
            ToastUtil.showShortToast("请输入城市");
            return;
        }
        RetrofitHelper.getInstance().createApi(DemoApi.class)
                .getWeather(city)
                .compose(RxScheduler.io_main_lifecycle(this))
                .map(new HongYangOpenApiRespFun<WeatherResp>())
                .subscribe(new BaseObserver<WeatherResp>() {
                    @Override
                    public void _onNext(WeatherResp weatherResp) {
                        ToastUtil.showShortToast("请求成功 "+ weatherResp.getWeather());
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast("请求失败 "+ e);
                    }
                });
    }

    private void queryIp() {
        RetrofitHelper.getInstance().createApi(DemoApi.class)
                .getIp()
                .compose(RxScheduler.io_main_lifecycle(this))
                .map(new Function<HongYangHttpResult<IpResp>, IpResp>() {
                    @Override
                    public IpResp apply(HongYangHttpResult<IpResp> ipRespHongYangHttpResult) throws Exception {
                        return ipRespHongYangHttpResult.data;
                    }
                })
                .subscribe(new BaseObserver<IpResp>() {
                    @Override
                    public void _onNext(IpResp ipResp) {
                        ToastUtil.showShortToast("请求成功 "+ ipResp.getCity());
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast("请求失败 "+ e);
                    }
                });
    }

    private void queryCity() {
        RetrofitHelper.getInstance().createApi(DemoApi.class)
                .getAllCity()
                .compose(RxScheduler.io_main_lifecycle(this))
                .map(new Function<HongYangHttpResult<List<CityResp>>, List<CityResp>>() {
                    @Override
                    public List<CityResp> apply(HongYangHttpResult<List<CityResp>> listHongYangHttpResult) throws Exception {
                        return listHongYangHttpResult.data;
                    }
                })
                .subscribe(new BaseObserver<List<CityResp>>() {
                    @Override
                    public void _onNext(List<CityResp> cityResps) {
                        ToastUtil.showShortToast("请求成功 "+ cityResps.size());
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast("请求失败 "+ e);
                    }
                });

    }
}
