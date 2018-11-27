package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.KnowledgeChildActivity;
import com.example.administrator.playandroid.adapter.KnowledgeAdapter;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.KnowledgeBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;
import com.example.handsomelibrary.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.administrator.playandroid.MainActivity.onScrollDown;
import static com.example.administrator.playandroid.MainActivity.onScrollUp;

/**
 * 知识体系
 * Created by Stefan on 2018/11/21.
 */
public class KnowledgeFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";
    @BindView(R.id.rv_knowList)
    RecyclerView rv_knowList;

//    @BindView(R.id.refreshLayout)
//    SwipeRefreshLayout refreshLayout;

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
        getKnowledgeTree();
    }

    /**
     * 网络请求知识体系数据
     */
    private void getKnowledgeTree() {
        RxHttpUtils.createApi(ApiService.class)
                .getKnowledgeTree()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<List<KnowledgeBean>>() {
                    @Override
                    protected void onSuccess(List<KnowledgeBean> knowledgeBeans) {
                        mAdapter.setNewData(knowledgeBeans);
                       // refreshLayout.setRefreshing(false);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                       // refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        setRvScroll();
    }

    private void initRv() {
        rv_knowList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new KnowledgeAdapter(new ArrayList<>());
        rv_knowList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            KnowledgeBean knowledgeBean = mAdapter.getData().get(position);
            Bundle bundle=new Bundle();
            bundle.putSerializable("knowledgeBean",knowledgeBean);
            JumpUtils.jump(mContext,KnowledgeChildActivity.class,bundle);
        });

        //refreshLayout.setColorSchemeColors(getResources().getColor(R.color.homeMain));
       // refreshLayout.setOnRefreshListener(this);
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

    //刷新列表
//    @Override
//    public void onRefresh() {
//        getKnowledgeTree();
//    }

    //  List list = new ArrayList();

//    public void setList() {
//
//        for (int i = 0; i < 10; i++) {
//            list.add("");
//        }
//    }


}
