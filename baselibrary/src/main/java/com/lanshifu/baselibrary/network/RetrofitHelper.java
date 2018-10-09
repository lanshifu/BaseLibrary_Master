package com.lanshifu.baselibrary.network;

import android.content.Context;
import android.text.TextUtils;

import com.lanshifu.baselibrary.app.MainApplication;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.cookies.CookieManger;
import com.lanshifu.baselibrary.network.interceptor.HttpLoggingInterceptor;
import com.lanshifu.baselibrary.network.progress.ProgressManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的单例
 * Created by lanxiaobin on 2017/9/6.
 */

public class RetrofitHelper {

    /**
     * 默认有OkHttpClient
     */
    private OkHttpClient mDefaultOkHttpClient;

    private Context mContext;

    private RetrofitHelper() {
        initOkHttp();
    }

    private static class Holder{
        static RetrofitHelper instance = new  RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return Holder.instance;
    }


    private void initOkHttp() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogHelper.d(message);
            }

            @Override
            public void json(String message) {
                LogHelper.json(message);
            }
        });

//        if (!BuildConfig.DEBUG){
        if (false){
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //ProgressManager 里面提供文件上传下载进度监听
        OkHttpClient.Builder builder = ProgressManager.getInstance().with(new OkHttpClient.Builder());

        if (mDefaultOkHttpClient == null) {
            builder.retryOnConnectionFailure(true) //错误重连
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .cookieJar(new CookieManger(MainApplication.getContext()))
                    .readTimeout(20, TimeUnit.SECONDS);

            mDefaultOkHttpClient = RetrofitUrlManager.getInstance().with(builder)
                    .build();
        }
    }

    /**
     * 统一添加请求头
     */
    private static class headerInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder builder = original.newBuilder()
                    .addHeader("system", "0")
                    .addHeader("model", "")  //手机型号
                    .addHeader("mac", "");
            if (!TextUtils.isEmpty("token")) {
                //除了第一次登陆没token，其它都加上token
                builder.addHeader("AccessToken", "token");
            }
            Request request = builder.build();
            return chain.proceed(request);
        }
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    public <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mDefaultOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    public  <T> T createApi(Class<T> clazz) {
        return createApi(clazz, ApiConstant.URL_DERAULT);
    }


}
