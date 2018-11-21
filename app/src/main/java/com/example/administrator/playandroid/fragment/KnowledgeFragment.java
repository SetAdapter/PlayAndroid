package com.example.administrator.playandroid.fragment;

import android.os.Bundle;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.base.BaseFragment;

/**
 * 知识体系
 * Created by Stefan on 2018/11/21.
 */
public class KnowledgeFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";

    public static KnowledgeFragment newInstance(String parmas){
        Bundle bundle=new Bundle();
        bundle.putString(HOME_FRAGMENT,parmas);
        KnowledgeFragment fragment=new KnowledgeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initData() {

    }
}
