package com.example.administrator.playandroid.fragment;

import android.os.Bundle;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.base.BaseFragment;

/**
 * 项目
 * Created by Stefan on 2018/11/21.
 */
public class ProjectFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";

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

    }
}
