package com.example.administrator.playandroid.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.AttentionEvent;
import com.example.administrator.playandroid.adapter.PubNumChildAdapter;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.PubNumChildBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

import static com.example.administrator.playandroid.activity.WebViewActivity.startWebActivity;

/**
 * 公众号
 * Created by Stefan on 2018/11/28 14:45
 */
public class PublicNumberChildFragment extends BaseFragment {
    @BindView(R.id.rv_pubNumChildList)
    RecyclerView rv_pubNumChildList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    PubNumChildAdapter mAdapter;
    int mPage = 0;
    public static PublicNumberChildFragment fragment;
    private int id;

    public static PublicNumberChildFragment getInstance() {
        fragment = new PublicNumberChildFragment();
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_public_num_child;
    }

    @Override
    protected void initData() {
        getWxArticleList(408,mPage);
        initRv();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void initRv() {
        rv_pubNumChildList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PubNumChildAdapter(new ArrayList<>());
        rv_pubNumChildList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startWebActivity(mContext,mAdapter.getData().get(position).getLink()));
        //刷新与分页加载
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage += 1;
                getWxArticleList(id, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                getWxArticleList(id, mPage);
            }
        });
    }

    //网络请求 公众号列表数据
    private void getWxArticleList(int id, int page) {
        RxHttpUtils.createApi(ApiService.class)
                .getWxArticleList(id, page)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<PubNumChildBean>() {
                    @Override
                    protected void onSuccess(PubNumChildBean pubNumChildBean) {
                        if (pubNumChildBean != null) {
                            if (refreshLayout.getState() == RefreshState.Refreshing) {
                                mAdapter.setNewData(pubNumChildBean.getDatas());
                                refreshLayout.finishRefresh();
                            }//正在加载
                            else if (refreshLayout.getState() == RefreshState.Loading) {
                                mAdapter.getData().addAll((pubNumChildBean.getDatas()));
                                refreshLayout.finishLoadMore();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.setNewData((pubNumChildBean.getDatas()));
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

                    }
                });
    }

    //接收id数据 来自PublicNumberFragment发送
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attendtion(AttentionEvent attentionEvent) {
        id = attentionEvent.getId();
        getWxArticleList(attentionEvent.getId(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

}
