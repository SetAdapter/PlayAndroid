package com.example.administrator.playandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.playandroid.fragment.HomeFragment;
import com.example.administrator.playandroid.fragment.KnowledgeFragment;
import com.example.administrator.playandroid.fragment.NavigationFragment;
import com.example.administrator.playandroid.fragment.ProjectFragment;
import com.example.administrator.playandroid.fragment.PublicNumberFragment;
import com.example.handsomelibrary.base.BaseActivity;
import com.example.handsomelibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * WanAndroid 首页
 * Created by Stefan on 2018/11/21 11:10.
 */

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    @BindView(R.id.fl_fragment)
    FrameLayout fl_fragment;

    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initBottomNavigation();
        initFragment();
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_fragment, mFragments.get(0));
        transaction.commit();
    }

    private void initFragment() {
        mFragments.add(HomeFragment.newInstance(""));
        mFragments.add(KnowledgeFragment.newInstance(""));
        mFragments.add(PublicNumberFragment.newInstance(""));
        mFragments.add(NavigationFragment.newInstance(""));
        mFragments.add(ProjectFragment.newInstance(""));
    }

    private void initBottomNavigation() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.
                addItem(new BottomNavigationItem(R.drawable.svg_home_n, getString(R.string.home)))
                .setActiveColor(R.color.homeMain)
                .addItem(new BottomNavigationItem(R.drawable.svg_knowledge_n, getString(R.string.knowledge)))
                .setActiveColor(R.color.homeMain)
                .addItem(new BottomNavigationItem(R.drawable.svg_publicnumber_n, getString(R.string.publicNumber)))
                .setActiveColor(R.color.homeMain)
                .addItem(new BottomNavigationItem(R.drawable.svg_navigation_n, getString(R.string.navigation)))
                .setActiveColor(R.color.homeMain)
                .addItem(new BottomNavigationItem(R.drawable.svg_project_n, getString(R.string.project)))
                .setActiveColor(R.color.homeMain)
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        if(null!=mFragments){
            if(position<=mFragments.size()){
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment currentFragment = fm.findFragmentById(R.id.fl_fragment);
                BaseFragment nextFragment = mFragments.get(position);
                if (nextFragment.isAdded()) {
                    ft.hide(currentFragment).show(nextFragment);
                } else {
                    ft.hide(currentFragment).add(R.id.fl_fragment, nextFragment);
                    if (nextFragment.isHidden()) {
                        ft.show(nextFragment);
                    }
                }
                ft.commitAllowingStateLoss();

            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (mFragments != null) {
            if (position < mFragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment nextFragment = mFragments.get(position);
                ft.hide(nextFragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
