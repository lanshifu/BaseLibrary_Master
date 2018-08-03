package com.lanshifu.video_module.network;

import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.video_module.bean.VideoListItemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoApi {

    @GET("/api/video")
    Observable<HttpResult<List<VideoListItemBean>>> getVideoList(@Query("page") int page,
                                                                 @Query("page_count") int page_count);

}
