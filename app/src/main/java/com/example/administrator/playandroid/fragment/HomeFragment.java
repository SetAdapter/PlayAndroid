package com.example.administrator.playandroid.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.base.BaseFragment;

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */
public class HomeFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";

    public static HomeFragment newInstance(String parmas){
        Bundle bundle=new Bundle();
        bundle.putString(HOME_FRAGMENT,parmas);
        HomeFragment fragment=new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }
}
