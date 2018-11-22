package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.ArticleListBean;

import java.util.List;

/**
 * Created by Stefan on 2018/11/22.
 */
public class HomeAdapter extends BaseQuickAdapter<ArticleListBean.DatasBean, BaseViewHolder> {
    public HomeAdapter(@Nullable List<ArticleListBean.DatasBean> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListBean.DatasBean item) {
        List<ArticleListBean.DatasBean.TagsBean> tags = item.getTags();
        TextView tv_label = helper.getView(R.id.tv_label);
        if (tags.size() > 0) {//设置标签 没有则消失
            tv_label.setVisibility(View.VISIBLE);
            tv_label.setText(tags.get(0).getName());
        } else {
            tv_label.setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_name,item.getAuthor())//作者姓名
                .setText(R.id.tv_time,item.getNiceDate())//时间
                .setText(R.id.tv_title,item.getTitle())//标题
                .setText(R.id.tv_superChapterName,item.getSuperChapterName())
                .setText(R.id.tv_chapterName,item.getChapterName());


    }
}
