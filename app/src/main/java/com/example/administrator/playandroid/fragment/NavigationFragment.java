package com.example.administrator.playandroid.fragment;

import android.os.Bundle;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.base.BaseFragment;

/**
 * 导航
 * Created by Stefan on 2018/11/21.
 */
public class NavigationFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";

    public static NavigationFragment newInstance(String parmas){
        Bundle bundle=new Bundle();
        bundle.putString(HOME_FRAGMENT,parmas);
        NavigationFragment fragment=new NavigationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initData() {

    }
}
