package com.example.handsomelibrary.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器  统一添加请求头使用
 * Created by Stefan on 2018/4/23 14:29.
 */

public class HeaderInterceptor implements Interceptor {

    private Map<String, Object> headerMaps;
    private Response proceed;
    private Request builder;

    public HeaderInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    @Override
    public Response intercept(Chain chain) {

        Request.Builder request = chain.request().newBuilder();
        if (headerMaps != null && headerMaps.size() > 0) {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }
        builder = request.build();
        try {
            proceed = chain.proceed(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return proceed;
    }
}
