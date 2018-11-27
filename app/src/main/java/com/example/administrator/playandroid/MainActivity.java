package com.example.administrator.playandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.playandroid.fragment.HomeFragment;
import com.example.administrator.playandroid.fragment.KnowledgeFragment;
import com.example.administrator.playandroid.fragment.NavigationFragment;
import com.example.administrator.playandroid.fragment.ProjectFragment;
import com.example.administrator.playandroid.fragment.PublicNumberFragment;
import com.example.handsomelibrary.base.BaseActivity;
import com.example.handsomelibrary.base.BaseFragment;
import com.example.handsomelibrary.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * WanAndroid 首页
 * Created by Stefan on 2018/11/21 11:10.
 */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    //  @BindView(R.id.tl_custom)
    public static Toolbar tl_custom;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    // @BindView(R.id.bottom_navigation_bar)
    public static BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.tv_collection)
    TextView tv_collection;

    private ActionBarDrawerToggle mDrawerToggle;
    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        tl_custom = findViewById(R.id.tl_custom);
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        initBottomNavigation();
        initFragment();
        setDefaultFragment();
        tl_custom.setTitle("WanAndroid");
        tl_custom.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(tl_custom);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, tl_custom, R.string.open, R.string.open) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //监听添加toolbar 菜单（就是搜索图标）
        tl_custom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        T.showShort("Search!");
                        break;
                }
                return true;
            }
        });

    }

    @OnClick({R.id.tv_collection, R.id.tv_todo, R.id.tv_nightMode, R.id.tv_setting, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collection:
                T.showShort("收藏");
                break;
            case R.id.tv_todo:
                T.showShort("TODO工具");
                break;
            case R.id.tv_nightMode:
                T.showShort("夜间模式");
                break;
            case R.id.tv_setting:
                T.showShort("设置");
                break;
            case R.id.tv_about:
                T.showShort("关于我们");
                break;
        }
    }

    //添加toolbar右侧 搜索图标
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (null != mFragments) {
            if (position <= mFragments.size()) {
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

                switch (position) {
                    case 0:
                        tl_custom.setTitle("WanAndroid");
                        break;
                    case 1:
                        tl_custom.setTitle(getString(R.string.knowledge));
                        break;
                    case 2:
                        tl_custom.setTitle(getString(R.string.publicNumber));
                        break;
                    case 3:
                        tl_custom.setTitle(getString(R.string.navigation));
                        break;
                    case 4:
                        tl_custom.setTitle(getString(R.string.project));
                        break;
                }


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

    /**
     * 下滑监听
     */
    public static void onScrollDown() {
        //下滑时要执行的代码
        //隐藏上下状态栏
        tl_custom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
        mBottomNavigationBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }

    /**
     * 上滑监听
     */
    public static void onScrollUp() {
        //上滑时要执行的代码 imageView.setVisibility(View.VISIBLE);
        //显示上下状态栏
        tl_custom.animate().translationY(-tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
        mBottomNavigationBar.animate().translationY(tl_custom.getHeight()).setInterpolator(new AccelerateInterpolator(3));
    }


}
