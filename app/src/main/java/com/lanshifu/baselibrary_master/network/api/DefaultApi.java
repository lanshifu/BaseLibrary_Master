package com.lanshifu.baselibrary_master.network.api;

import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.baselibrary_master.bean.PictureResp;
import com.lanshifu.baselibrary_master.bean.TempBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018\5\21 0021.
 */

public interface DefaultApi {


    @GET("/api/picture")
    Observable<HttpResult<List<PictureResp>>> getDailyList(@Query("page") int page,
                                        @Query("page_count") int page_count);

    @GET("/tools/mockapi/1871/getGroupTempLate")
    @Headers({"Content-Type: application/json","Accept: application/json","Domain-Name: wanandroid"})
    Observable<TempBean> getTempList();


    @POST("/api/register")
    Observable<HttpResult<String>> register(@Body RequestBody info);

}
