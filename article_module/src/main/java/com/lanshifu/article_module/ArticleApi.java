package com.lanshifu.article_module;

import com.lanshifu.article_module.bean.ArticleBean;
import com.lanshifu.baselibrary.network.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lanshifu on 2018/11/30.
 */

public interface ArticleApi {


    @GET("/api/article")
    Observable<HttpResult<List<ArticleBean>>> getArtileList(@Query("page") int page,
                                                            @Query("page_count") int page_count);



    @GET("/api/article_detail")
    Observable<HttpResult<ArticleBean>> getArtileDetail(@Query("id") int id);
}
