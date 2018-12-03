package com.example.administrator.playandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.AttentionEvent;
import com.example.administrator.playandroid.adapter.ProjectChildAdapter;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.ProjectChildBean;
import com.example.handsomelibrary.model.PubNumChildBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 项目子界面
 * Created by Stefan on 2018/12/3 15:03
 */
public class ProjectChildFragment extends BaseFragment {

    @BindView(R.id.rv_ChildList)
    RecyclerView rv_ChildList;

    int mPage = 0;
    public static ProjectChildFragment fragment;
    private int id;
    ProjectChildAdapter mAdapter;

    public static ProjectChildFragment getInstance() {
        fragment = new ProjectChildFragment();
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_project_child;
    }

    @Override
    protected void initData() {
        getProjectList(0, 294);
        initRv();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void initRv() {
        rv_ChildList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ProjectChildAdapter(new ArrayList<>());
        rv_ChildList.setAdapter(mAdapter);
    }

    //网络请求 项目列表数据
    private void getProjectList(int page, int id) {
        RxHttpUtils.createApi(ApiService.class)
                .getProjectList(page, id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ProjectChildBean>() {
                    @Override
                    protected void onSuccess(ProjectChildBean projectChildBean) {
                        if (projectChildBean != null) {
                                mAdapter.setNewData(projectChildBean.getDatas());
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

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
