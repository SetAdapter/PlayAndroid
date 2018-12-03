package com.example.handsomelibrary.api;


import android.util.ArrayMap;

import com.example.handsomelibrary.model.ArticleListBean;
import com.example.handsomelibrary.model.BannerBean;
import com.example.handsomelibrary.model.BaseBean;
import com.example.handsomelibrary.model.KnowledgeBean;
import com.example.handsomelibrary.model.KnowledgeChildBean;
import com.example.handsomelibrary.model.LoginBean;
import com.example.handsomelibrary.model.ProjectChildBean;
import com.example.handsomelibrary.model.ProjectTreeBean;
import com.example.handsomelibrary.model.PubNumChildBean;
import com.example.handsomelibrary.model.WxArticleBean;

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
     * 2.1 体系数据
     */
    @GET("tree/json")
    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeTree();

    /**
     * 2.2 知识体系下的文章
     */
    @GET("article/list/{page}/json")
    Observable<BaseBean<KnowledgeChildBean>> getKnowledgeChild(@Path("page") int page,
                                                               @Query("cid") int cid);

    /**
     * 获取公众号列表
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseBean<List<WxArticleBean>>> getWxArticle();

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseBean<PubNumChildBean>> getWxArticleList(@Path("id") int id, @Path("page") int page);


    /**
     * 4.1 项目分类
     */
    @GET("project/tree/json")
    Observable<BaseBean<List<ProjectTreeBean>>> getProjectTree();

    /**
     * 4.2 项目列表数据
     */
    @GET("project/list/{page}/json")
    Observable<BaseBean<ProjectChildBean>> getProjectList(@Path("page") int page,
                                                          @Query("cid") int cid);












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

