package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.KnowledgeChildBean;
import com.example.handsomelibrary.model.PubNumChildBean;

import java.util.List;
import java.util.Random;

/**
 * Created by Stefan on 2018/11/27 15:39
 */
public class PubNumChildAdapter extends BaseQuickAdapter<PubNumChildBean.DatasBean, BaseViewHolder> {
    public PubNumChildAdapter(@Nullable List<PubNumChildBean.DatasBean> data) {
        super(R.layout.item_knowledge_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PubNumChildBean.DatasBean item) {
        helper.setText(R.id.tv_name,item.getAuthor())//作者名字
                .setText(R.id.tv_time, item.getNiceDate())//时间
                .setText(R.id.tv_title, item.getTitle())//标题
                .setText(R.id.tv_superChapterName, item.getSuperChapterName())
                .setText(R.id.tv_chapterName, item.getChapterName());

        //随机作者名字颜色
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        helper.setTextColor(R.id.tv_name, ranColor);

    }
}
