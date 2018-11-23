package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.ArticleListBean;

import java.util.List;
import java.util.Random;

/**
 * Created by Stefan on 2018/11/22.
 */
public class HomeAdapter extends BaseQuickAdapter<ArticleListBean.DatasBean, BaseViewHolder> {
    List<ArticleListBean.DatasBean> data;
    boolean isClick;

    public HomeAdapter(@Nullable List<ArticleListBean.DatasBean> data) {
        super(R.layout.item_home, data);
        this.data = data;
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
        helper.setText(R.id.tv_name, item.getAuthor())//作者姓名
                .setText(R.id.tv_time, item.getNiceDate())//时间
                .setText(R.id.tv_title, item.getTitle())//标题
                .setText(R.id.tv_superChapterName, item.getSuperChapterName())
                .setText(R.id.tv_chapterName, item.getChapterName());
        ImageView iv_collection = helper.getView(R.id.iv_collection);
        //收藏按钮

//        iv_collection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isClick){
//                    iv_collection.setImageResource(R.drawable.svg_collection);
//                    isClick=false;
//                }else {
//                    iv_collection.setImageResource(R.drawable.svg_collection_y);
//                    isClick=true;
//                }
//
//            }
//        });
        helper.addOnClickListener(R.id.iv_collection);

        //随机作者名字颜色
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        helper.setTextColor(R.id.tv_name, ranColor);

        //设置条目图片
        ImageView iv_img = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            Glide.with(mContext)                             //配置上下文
                    .load(item.getEnvelopePic())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存全尺寸
                    .into(iv_img);
        } else {
            iv_img.setVisibility(View.GONE);
        }
    }
}