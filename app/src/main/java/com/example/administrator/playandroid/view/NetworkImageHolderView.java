package com.example.administrator.playandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.BannerBean;

/**
 * @author Sai
 * @date 15/8/4
 * 轮播图 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<BannerBean> {
    private ImageView imageView;
    private View banner_bottom_text;
    private TextView tv_bannerText;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
//        imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        banner_bottom_text = LayoutInflater.from(context).inflate(R.layout.banner_bottom_text, null);
        imageView = banner_bottom_text.findViewById(R.id.iv_banner);
        tv_bannerText = banner_bottom_text.findViewById(R.id.tv_bannerText);

        return banner_bottom_text;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean data) {
        imageView.setImageResource(R.drawable.ic_launcher_background);
        tv_bannerText.setText(data.getTitle());
        Glide.with(context)                             //配置上下文
                .load(data.getImagePath())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存全尺寸
                .into(imageView);
    }
}
