package com.lanshifu.demo_module.test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKhttp3Test {

    public static void request() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url("http:.//www.baidu.com");
        Request request = builder.build();
        Response response = client.newCall(request).execute();



    }
}
