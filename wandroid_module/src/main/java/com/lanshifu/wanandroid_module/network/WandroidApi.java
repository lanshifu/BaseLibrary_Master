package com.lanshifu.wanandroid_module.network;

import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.wanandroid_module.bean.WandroidResult;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WandroidApi {

    @FormUrlEncoded
    @POST("/user/login")
    @Headers({"Domain-Name: wanandroid"})
    Observable<WandroidResult> login(@Field("username") String username,
                                     @Field("password") String password);

    @GET("/lg/collect/list/{page}/json")
    @Headers({"Domain-Name: wanandroid"})
    Observable<WandroidResult> collectList(@Path("page") int page);

}
