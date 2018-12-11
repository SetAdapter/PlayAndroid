package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.adapter.NavigationBeanSection;
import com.example.administrator.playandroid.adapter.NavigationLeftAdapter;
import com.example.administrator.playandroid.adapter.NavigationRightAdapter;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.NavigationBean;
import com.example.handsomelibrary.model.ProjectTreeBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 导航
 * Created by Stefan on 2018/11/21.
 */
public class NavigationFragment extends BaseFragment {

    private static final String HOME_FRAGMENT = "home";

    @BindView(R.id.rv_left)
    RecyclerView rv_left;
    @BindView(R.id.rv_right)
    RecyclerView rv_right;

    NavigationLeftAdapter leftAdapter;

    NavigationRightAdapter rightAdapter;

    public static NavigationFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(HOME_FRAGMENT, params);
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initData() {
        initRv();
        getNavigationList();
    }

    private void initRv() {
        //左边栏
        rv_left.setLayoutManager(new LinearLayoutManager(mContext));
        leftAdapter = new NavigationLeftAdapter(new ArrayList<>());
        rv_left.setAdapter(leftAdapter);
        //右边栏
        rv_right.setLayoutManager(new GridLayoutManager(mContext ,2));
        rightAdapter=new NavigationRightAdapter(new ArrayList<>());
        rv_right.setAdapter(rightAdapter);

        leftAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rb_left:
                    leftAdapter.setCheckPosition(position);
                    leftAdapter.notifyDataSetChanged();
                    break;
            }
        });
    }

    private void getNavigationList() {
        RxHttpUtils.createApi(ApiService.class)
                .getNavigationList()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<List<NavigationBean>>() {
                    @Override
                    protected void onSuccess(List<NavigationBean> navigationBeans) {
                        if(navigationBeans!=null){
                            leftAdapter.setNewData(navigationBeans);
                            //添加右边分组数据
                            List<NavigationBeanSection> sections=new ArrayList<>();
                            for (NavigationBean beans: navigationBeans) {
                                if(!TextUtils.isEmpty(beans.getName())){
                                    sections.add(new NavigationBeanSection(true,beans.getName()));//头部数据
                                    //item需要的数据
                                    for (NavigationBean.ArticlesBean articlesBean: beans.getArticles()){
                                        sections.add(new NavigationBeanSection(articlesBean));
                                    }
                                }
                            }
                            rightAdapter.setNewData(sections);
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    //    private void setTagFlow() {
//        fl_tags.setAdapter(new TagAdapter<String>(setTagData()) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                View inflate = LayoutInflater.from(mContext).inflate(R.layout.tags_tv, fl_tags, false);
//                TextView tv_tags = inflate.findViewById(R.id.tv_tags);
//                tv_tags.setText(s);
//                return inflate;
//            }
//        });
//        fl_tags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                setHotSearchResult(position);
//                return true;
//            }
//        });
//    }

}
