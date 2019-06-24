package com.lanshifu.demo_module.network;

import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.demo_module.bean.CityResp;
import com.lanshifu.demo_module.bean.HongYangHttpResult;
import com.lanshifu.demo_module.bean.IpResp;
import com.lanshifu.demo_module.bean.WeatherResp;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DemoApi {


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //断点续传，传Range header
    @GET
    @Streaming
    Observable<ResponseBody> download(@Header("Range") String range, @Url String url);

    @GET("/api/video")
    Observable<HttpResult> request(@Query("page") int page,
                                         @Query("page_count") int page_count,
                                         @Query("type") int type);


    /** https://github.com/MZCretin/RollToolsApi */
    //全国城市列表
    @GET("/api/address/list")
    @Headers({"Domain-Name: " + MainApplication.DOMAIN_GOPEN_API})
    Observable<HongYangHttpResult<List<CityResp>>> getAllCity();


    //获取访问者ip
    @Headers({"Domain-Name: " + MainApplication.DOMAIN_GOPEN_API})
    @GET("/api/ip/self")
    Observable<HongYangHttpResult<IpResp>> getIp();


    //获取天气
    //参数说明： {城市名} 传入你需要查询的城市，请尽量传入完整值，否则系统会自行匹配，可能会有误差
    @Headers({"Domain-Name: " + MainApplication.DOMAIN_GOPEN_API})
    @GET("/api/weather/current/{city}")
    Observable<HongYangHttpResult<WeatherResp>> getWeather(@Path("city") String city);



}
