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
import com.example.handsomelibrary.model.WxArticleBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 公众号
 * Created by Stefan on 2018/11/21.
 */
public class PublicNumberFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private static final String HOME_FRAGMENT = "home";

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.ViewPager)
    ViewPager mViewPager;

    public static PublicNumberFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(HOME_FRAGMENT, params);
        PublicNumberFragment fragment = new PublicNumberFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_public_number;
    }

    @Override
    protected void initData() {
        getWxArticle();
    }

    private void getWxArticle() {
        RxHttpUtils.createApi(ApiService.class)
                .getWxArticle()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<List<WxArticleBean>>() {
                    @Override
                    protected void onSuccess(List<WxArticleBean> wxArticleBeans) {
                        if (wxArticleBeans != null) {
                            initTab(wxArticleBeans);
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    private void initTab(List<WxArticleBean> wxArticleBeans) {
        //设置ViewPager
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PublicNumberChildFragment.getInstance();
            }

            @Override
            public int getCount() {
                return wxArticleBeans.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return wxArticleBeans.get(position).getName();
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
                EventBus.getDefault().post(new AttentionEvent(wxArticleBeans.get(tab.getPosition()).getId()));
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
