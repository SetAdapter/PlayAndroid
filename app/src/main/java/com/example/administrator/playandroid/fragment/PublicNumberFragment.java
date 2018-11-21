package com.example.administrator.playandroid.fragment;

import android.os.Bundle;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.base.BaseFragment;

/**
 * 公众号
 * Created by Stefan on 2018/11/21.
 */
public class PublicNumberFragment extends BaseFragment{

    private static final String HOME_FRAGMENT = "home";

    public static PublicNumberFragment newInstance(String parmas){
        Bundle bundle=new Bundle();
        bundle.putString(HOME_FRAGMENT,parmas);
        PublicNumberFragment fragment=new PublicNumberFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_public_number;
    }

    @Override
    protected void initData() {

    }
}
