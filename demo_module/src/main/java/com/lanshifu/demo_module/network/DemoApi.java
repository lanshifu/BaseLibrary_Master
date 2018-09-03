package com.lanshifu.demo_module.network;

import com.lanshifu.baselibrary.network.HttpResult;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
}
