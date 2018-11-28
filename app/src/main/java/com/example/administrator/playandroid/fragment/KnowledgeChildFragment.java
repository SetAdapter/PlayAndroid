package com.example.administrator.playandroid.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.AttentionEvent;
import com.example.administrator.playandroid.adapter.KnowledgeChildAdapter;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.KnowledgeBean;
import com.example.handsomelibrary.model.KnowledgeChildBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;
import com.example.handsomelibrary.utils.L;
import com.example.handsomelibrary.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Stefan on 2018/11/27 15:33
 */
public class KnowledgeChildFragment extends BaseFragment {
    @BindView(R.id.rv_knowChildList)
    RecyclerView rv_knowChildList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    KnowledgeChildAdapter mAdapter;
    public static KnowledgeChildFragment fragment;

    int mPage = 0;
    private int cid;

    public static KnowledgeChildFragment getInstance() {
        fragment = new KnowledgeChildFragment();
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_knowledge_child;
    }

    @Override
    protected void initData() {
        initRv();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void initRv() {
        getKnowledgeChild(mPage, 60);
        rv_knowChildList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new KnowledgeChildAdapter(new ArrayList<>());
        rv_knowChildList.setAdapter(mAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage += 1;
                getKnowledgeChild(mPage, cid);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                getKnowledgeChild(mPage, cid);
            }
        });
    }

    //网络请求知识体系 二级数据
    public void getKnowledgeChild(int page, int cid) {
        RxHttpUtils.createApi(ApiService.class)
                .getKnowledgeChild(page, cid)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<KnowledgeChildBean>() {
                    @Override
                    protected void onSuccess(KnowledgeChildBean childBeans) {
                        if (childBeans != null) {
                            if (refreshLayout.getState() == RefreshState.Refreshing) {
                                mAdapter.setNewData(childBeans.getDatas());
                                refreshLayout.finishRefresh();
                            }//正在加载
                            else if (refreshLayout.getState() == RefreshState.Loading) {
                                mAdapter.getData().addAll((childBeans.getDatas()));
                                refreshLayout.finishLoadMore();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.setNewData((childBeans.getDatas()));
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

    //接收id数据 来自KnowledgeChildActivity发送
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attendtion(AttentionEvent attentionEvent) {
        cid = attentionEvent.getId();
        getKnowledgeChild(0, attentionEvent.getId());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
