package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.adapter.KnowledgeAdapter;
import com.example.handsomelibrary.base.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.administrator.playandroid.MainActivity.onScrollDown;
import static com.example.administrator.playandroid.MainActivity.onScrollUp;

/**
 * 知识体系
 * Created by Stefan on 2018/11/21.
 */
public class KnowledgeFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    private static final String HOME_FRAGMENT = "home";
    @BindView(R.id.rv_knowList)
    RecyclerView rv_knowList;

    private Toolbar tl_custom;
    private BottomNavigationBar bottomBar;
    KnowledgeAdapter mAdapter;

    public static KnowledgeFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(HOME_FRAGMENT, params);
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initData() {
        initRv();
    }

    @Override
    protected void initView() {
        super.initView();
        tl_custom = mContext.findViewById(R.id.tl_custom);
        bottomBar = mContext.findViewById(R.id.bottom_navigation_bar);
        setRvScroll();
    }

    private void initRv() {
        setList();
        rv_knowList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new KnowledgeAdapter(list);
        rv_knowList.setAdapter(mAdapter);
    }

    //RecyclerView 滑动监听
    private void setRvScroll() {
        rv_knowList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    List list = new ArrayList();
    public void setList() {

        for (int i = 0; i < 10; i++) {
            list.add("");
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
