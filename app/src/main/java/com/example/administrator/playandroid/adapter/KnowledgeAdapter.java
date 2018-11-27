package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.KnowledgeBean;

import java.util.List;

/**
 * Created by Stefan on 2018/11/26 17:33
 */
public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeBean, BaseViewHolder> {
    public KnowledgeAdapter(@Nullable List<KnowledgeBean> data) {
        super(R.layout.item_knowledge, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeBean item) {
        helper.setText(R.id.tv_title, item.getName());
        TextView tv_content = helper.getView(R.id.tv_content);
        StringBuffer sb = new StringBuffer();
        List<KnowledgeBean.ChildrenBean> itemChildren = item.getChildren();
        for (KnowledgeBean.ChildrenBean t : itemChildren) {
            sb.append(t.getName() + "   ");
        }
        tv_content.setText(sb.toString());
    }
}
