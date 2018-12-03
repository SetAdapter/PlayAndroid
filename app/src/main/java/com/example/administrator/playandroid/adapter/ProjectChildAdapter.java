package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.ProjectChildBean;
import com.example.handsomelibrary.model.PubNumChildBean;

import java.util.List;
import java.util.Random;

/**
 * Created by Stefan on 2018/11/27 15:39
 */
public class ProjectChildAdapter extends BaseQuickAdapter<ProjectChildBean.DatasBean, BaseViewHolder> {
    public ProjectChildAdapter(@Nullable List<ProjectChildBean.DatasBean> data) {
        super(R.layout.item_project_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectChildBean.DatasBean item) {
        helper.setText(R.id.tv_author, item.getAuthor())//作者名字
                .setText(R.id.tv_niceDate, item.getNiceDate())//时间
                .setText(R.id.tv_title, item.getTitle())//标题
                .setText(R.id.tv_desc, item.getDesc());
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


        //随机作者名字颜色
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        helper.setTextColor(R.id.tv_title, ranColor);

    }
}
