package com.example.handsomelibrary.api;


import android.util.ArrayMap;

import com.example.handsomelibrary.model.ArticleListBean;
import com.example.handsomelibrary.model.BannerBean;
import com.example.handsomelibrary.model.BaseBean;
import com.example.handsomelibrary.model.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 接口 API
 * Created by Stefan on 2018/4/23.
 */

public interface ApiService {

    String BASE_URL = "http://www.wanandroid.com/";
    //    String BASE_URL = "http://192.168.100.251:8089/";
    String BASE_PIC_URL = "http://116.196.95.169/file/getImages?imageurl=";
//    String BASE_PIC_URL = "http://192.168.100.251:8089/file/getImages?imageurl=";

    /**
     * 1.1 首页文章列表
     */
    @GET("article/list/{id}/json")
    Observable<BaseBean<ArticleListBean>> getArticleList(@Path("id") int id);

    /**
     * 1.2 首页banner
     */
    @GET("banner/json")
    Observable<BaseBean<List<BannerBean>>> getBanner();


    /**
     * 4.2.重新发送激活邮件
     *
     * @param username
     * @return
     */
    @GET("app/user/sendEmailToAlive")
    Observable<BaseBean<Object>> sendEmailToAlive(@Query("username") String username);

    /**
     * 4.5.登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/loginToApp")
    Observable<LoginBean> login(@Field("username") String username
            , @Field("password") String password
            , @Field("regSource") int regSource);


    /**
     * 4.7.意见反馈
     */
    @POST("app/feedback/save")
    Observable<BaseBean<String>> feedback(@Body ArrayMap<String, Object> options);

    /**
     * 4.8.获取图片地址
     */
    @GET("/file/getImages")
    Observable<BaseBean<String>> getImages(@Query("imageurl") String cusId);


}

