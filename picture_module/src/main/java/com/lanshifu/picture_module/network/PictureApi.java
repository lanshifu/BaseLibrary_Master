package com.lanshifu.picture_module.network;

import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.picture_module.bean.PictureListItemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PictureApi {

    @GET("/api/picture")
    Observable<HttpResult<List<PictureListItemBean>>> getPictureList(@Query("page") int page,
                                                                     @Query("page_count") int page_count);

}
