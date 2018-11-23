package com.example.handsomelibrary.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.handsomelibrary.R;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.HashMap;
import java.util.Map;

import static com.example.handsomelibrary.api.ApiService.BASE_URL;


/**
 * 基础 application
 * Created by Stefan on 2018/4/20.
 */

public class BaseApplication extends Application {

    private static BaseApplication mApplication; // 单例模式

    /**
     * 单例模式，获取Application的实例
     *
     * @return
     */
    public static BaseApplication getApplication() {
        return mApplication;
    }

    public static Context getAppContext() {
        return mApplication;
    }

    public static Resources getAppResources() {
        return mApplication.getResources();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Map<String, Object> headerMaps = new HashMap<>();
//        headerMaps.put("token", SpfUtils.getSpfSaveStr(Constant.token));
       // registerActivityLifecycleCallbacks(new ActivityLifecycleCallback());

        /**
         * 初始化配置
         */
        RxHttpUtils.init(this);

        RxHttpUtils.getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(BASE_URL)
               // .setHeaders(headerMaps)
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
//                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setLog(true);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.txtSecondColor);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
