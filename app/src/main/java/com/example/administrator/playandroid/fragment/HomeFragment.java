package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.WebViewActivity;
import com.example.administrator.playandroid.adapter.HomeAdapter;
import com.example.administrator.playandroid.view.NetworkImageHolderView;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.ArticleListBean;
import com.example.handsomelibrary.model.BannerBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;
import com.example.handsomelibrary.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.administrator.playandroid.MainActivity.onScrollDown;
import static com.example.administrator.playandroid.MainActivity.onScrollUp;

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */
public class HomeFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.rv_main)
    RecyclerView rv_main;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private static final String HOME_FRAGMENT = "home";

    HomeAdapter mAdapter;
    int mPageNo = 0;
    private ConvenientBanner bannerView;
    private View headerView;
    private List<ArticleListBean.DatasBean> datas;

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
        initRv();
        setAnimation();
    }

    private void setAnimation() {

    }

    private void initRv() {
        //首页banner数据
        getBanner();
        //列表数据
        getArticleList(mPageNo);
        rv_main.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new HomeAdapter(new ArrayList<ArticleListBean.DatasBean>());
        rv_main.setAdapter(mAdapter);
        // rv_main.addOnScrollListener(new FabScrollListener(this));
        setRvScroll();

        //添加头部
        headerView = LayoutInflater.from(mContext).inflate(R.layout.home_banner, null);
        bannerView = headerView.findViewById(R.id.convenientBanner);
        mAdapter.addHeaderView(headerView);
        refreshLayout.setOnRefreshLoadMoreListener(this);//监听刷新
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<ArticleListBean.DatasBean> data = mAdapter.getData();
            if (data.size() != 0 && datas != null) {
                WebViewActivity.startWebActivity(mContext, data.get(position).getLink());
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            ImageView iv_collection = view.findViewById(R.id.iv_collection);
            switch (view.getId()) {
                case R.id.iv_collection:
                    if (isClick) {
                        iv_collection.setImageResource(R.drawable.svg_collection);
                        isClick = false;
                    } else {
                        iv_collection.setImageResource(R.drawable.svg_collection_y);
                        isClick = true;
                    }
                    break;
            }
        });
    }

    boolean isClick;

    //网络请求Banner数据
    private void getBanner() {
        RxHttpUtils.createApi(ApiService.class)
                .getBanner()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<List<BannerBean>>() {
                    @Override
                    protected void onSuccess(List<BannerBean> t) {
//                        if (bannerBeans != null) {
//                            for (BannerBean Bean : t) {
//                                bannerBeans.add(Bean.getImagePath());
//                            }
                        setBannerView(t);
//                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });

    }

    // List<String> bannerBeans = new ArrayList<>();

    private void setBannerView(List<BannerBean> bannerBean) {
        bannerView.setPages(() -> new NetworkImageHolderView(), bannerBean)
                //设置指示器是否可见
                //.setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.svg_indicator_n, R.drawable.svg_indicator})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                //设置点击监听事件
                .setOnItemClickListener(position -> {
                    // todo 点击 轮播图跳转到指定界面
                })
                //设置手动影响（设置了该项无法手动切换）
                .setManualPageable(true);

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
        });
    }


    //网络请求首页文章列表
    private void getArticleList(int mPageNo) {
        RxHttpUtils.createApi(ApiService.class)
                .getArticleList(mPageNo)
                .compose(Transformer.<ArticleListBean>switchSchedulers())
                .subscribe(new CommonObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccess(ArticleListBean listBean) {
                        if (listBean != null) {
                            datas = listBean.getDatas();
//                            //正在刷新
                            if (refreshLayout.getState() == RefreshState.Refreshing) {
                                mAdapter.setNewData(datas);
                                refreshLayout.finishRefresh();
                            }//正在加载
                            else if (refreshLayout.getState() == RefreshState.Loading) {
                                mAdapter.getData().addAll(datas);
                                refreshLayout.finishLoadMore();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.setNewData(datas);
                            }
                        } else {
                            if (refreshLayout.getState() == RefreshState.Refreshing) {
                                refreshLayout.finishRefresh();
                            } else if (refreshLayout.getState() == RefreshState.Loading) {
                                refreshLayout.finishLoadMore();
                            }
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        T.showShort(errorMsg);
                    }
                });
    }

    /**
     * 分页加载数据
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNo += 1;
        getArticleList(mPageNo);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPageNo = 0;
        getArticleList(mPageNo);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.getState() == RefreshState.Loading) {
            refreshLayout.finishLoadMore();
        }
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

