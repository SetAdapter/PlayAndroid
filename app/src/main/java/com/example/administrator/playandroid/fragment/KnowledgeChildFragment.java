package com.example.administrator.playandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.adapter.KnowledgeChildAdapter;
import com.example.handsomelibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Stefan on 2018/11/27 15:33
 */
public class KnowledgeChildFragment extends BaseFragment {
    @BindView(R.id.rv_knowChildList)
    RecyclerView rv_knowChildList;

    KnowledgeChildAdapter mAdapter;
    public static KnowledgeChildFragment fragment;

    public static KnowledgeChildFragment getInstance() {
        fragment = new KnowledgeChildFragment();
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_knowledge_child;
    }

    @Override
    protected void initData() {
        initRv();
    }

    private void initRv() {
        setList();
        rv_knowChildList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new KnowledgeChildAdapter(list);
        rv_knowChildList.setAdapter(mAdapter);
    }

    List list = new ArrayList();
    public void setList() {

        for (int i = 0; i < 10; i++) {
            list.add("");
        }
    }
}
