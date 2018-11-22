package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.adapter.HomeAdapter;
import com.example.administrator.playandroid.interfacer.FabScrollListener;
import com.example.administrator.playandroid.interfacer.HideScrollListener;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.ArticleListBean;
import com.example.handsomelibrary.model.BaseBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_main)
    RecyclerView rv_main;
    private static final String HOME_FRAGMENT = "home";

    HomeAdapter adapter;
    private Toolbar tl_custom;
    private BottomNavigationBar bottombar;

    public static HomeFragment newInstance(String parmas) {
        Bundle bundle = new Bundle();
        bundle.putString(HOME_FRAGMENT, parmas);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        setRv();
        setAnimation();
    }

    private void setAnimation() {

    }

    private void setRv() {
        getArticleList();
        tl_custom = mContext.findViewById(R.id.tl_custom);
        bottombar = mContext.findViewById(R.id.bottom_navigation_bar);
        rv_main.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new HomeAdapter(new ArrayList<ArticleListBean.DatasBean>());
        rv_main.setAdapter(adapter);
        // rv_main.addOnScrollListener(new FabScrollListener(this));
        setRvScroll();

        //添加头部
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_banner, null);
        adapter.addHeaderView(view);
    }

    //RecyclerView 滑动监听
    private void setRvScroll() {
        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 0) {
                        onScrollUp();
                    } else {
                        onScrollDown();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            public void setScrollThreshold(int scrollThreshold) {
                mScrollThreshold = scrollThreshold;
            }
        });
    }

    /**
     * 下滑监听
     */
    private void onScrollDown() {
        //下滑时要执行的代码
        //隐藏上下状态栏
        tl_custom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
        bottombar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }

    /**
     * 上滑监听
     */
    private void onScrollUp() {
        //上滑时要执行的代码 imageView.setVisibility(View.VISIBLE);
        //显示上下状态栏
        tl_custom.animate().translationY(-tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
        bottombar.animate().translationY(tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
    }

    //网络请求首页文章列表
    private void getArticleList() {
        RxHttpUtils.createApi(ApiService.class)
                .getArticleList()
                .compose(Transformer.<ArticleListBean>switchSchedulers())
                .subscribe(new CommonObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccess(ArticleListBean listBean) {
                        if(listBean!=null){
                            List<ArticleListBean.DatasBean> datas = listBean.getDatas();
                            adapter.setNewData(datas);
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

//    @Override
//    public void onHide() {
//        //隐藏动画
//        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                tl_custom.animate().translationY(-tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
//            }
//        });
//
//    }
//
//    @Override
//    public void onShow() {
//
//        // 显示动画
//
//        //这个方法用来  判断你的RecyclerView是否滑到第一条，就是顶部，只有到顶部才让toolbar显示出来
//        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.i("TAG", "--------------------------------------");
////
////                if (rv_main.canScrollVertically(1)) { //还能向下滑动
////                    Log.i("TAG", "direction 1: true");
////                    //显示
////                    tl_custom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
////                } else {//滑动到底部
////                    Log.i("TAG", "direction 1: false");
////                }
////                //还能向上滑动
////                if (rv_main.canScrollVertically(-1)) {
////                    Log.i("TAG", "direction -1: true");
////                    //隐藏
////                    tl_custom.animate().translationY(-tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
////                } else {
////                    //显示toolbar
////                   // tl_custom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
////                    Log.i("TAG", "direction -1: false");//滑动到顶部
////                }
//                if (dy < 0) {
//                    tl_custom.animate().translationY(-tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
//                } else if (dy > 0) {
//                    tl_custom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
//                }
//
//
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//        //也可以设置只要一往下滑就显示出来toolbar  看情况而定吧
////        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
////        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
////        imageButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
////
//    }
}

