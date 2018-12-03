package com.example.administrator.playandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.activity.AttentionEvent;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.ProjectTreeBean;
import com.example.handsomelibrary.model.WxArticleBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 项目
 * Created by Stefan on 2018/11/21.
 */
public class ProjectFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private static final String HOME_FRAGMENT = "home";
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.ViewPager)
    ViewPager mViewPager;

    public static ProjectFragment newInstance(String parmas){
        Bundle bundle=new Bundle();
        bundle.putString(HOME_FRAGMENT,parmas);
        ProjectFragment fragment=new ProjectFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initData() {
        getProjectTree();
    }

    private void getProjectTree() {
        RxHttpUtils.createApi(ApiService.class)
                .getProjectTree()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<List<ProjectTreeBean>>() {
                    @Override
                    protected void onSuccess(List<ProjectTreeBean> projectTreeBeans) {
                        if (projectTreeBeans != null) {
                            initTab(projectTreeBeans);
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    private void initTab(List<ProjectTreeBean> projectTreeBeans) {
        //设置ViewPager
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ProjectChildFragment.getInstance();
            }

            @Override
            public int getCount() {
                return projectTreeBeans.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return projectTreeBeans.get(position).getName();
            }
        });
        mViewPager.setOffscreenPageLimit(4); //设置ViewPager预加载 貌似并没有貌似卵用
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);

        //TabLayout的Tab选择监听
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //发送事件数据（公众号作者的id）至 PublicNumberChildFragment
                //TODO 这是复制上面的 公用了一个中转类
                EventBus.getDefault().post(new AttentionEvent(projectTreeBeans.get(tab.getPosition()).getId()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
