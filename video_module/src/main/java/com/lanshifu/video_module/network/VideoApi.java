package com.lanshifu.video_module.network;

import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.video_module.bean.VideoListItemBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface VideoApi {

    @GET("/api/video")
    Observable<HttpResult<List<VideoListItemBean>>> getVideoList(@Query("page") int page,
                                                                 @Query("page_count") int page_count,
                                                                 @Query("type") int type);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
