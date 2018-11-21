package com.example.handsomelibrary.retrofit;

import okhttp3.OkHttpClient;

/**
 * okHttp client
 * Created by Stefan on 2018/4/23.
 */

public class HttpClient {
    private static volatile HttpClient instance;
    private OkHttpClient.Builder builder;

    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
    }



    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }




}