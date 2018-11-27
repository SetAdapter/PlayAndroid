package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;

import java.util.List;

/**
 * Created by Stefan on 2018/11/27 15:39
 */
public class KnowledgeChildAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public KnowledgeChildAdapter(@Nullable List<String> data) {
        super(R.layout.item_knowledge_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
