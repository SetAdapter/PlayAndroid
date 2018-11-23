package com.example.administrator.playandroid.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.handsomelibrary.model.BannerBean;

/**
 * 本地图片Holder
 * Created by fangs on 2017/7/6.
 */
public class LocalImageHolderView implements Holder<BannerBean> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {

        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean banner) {
        Glide.with(context)
                .load(banner.getImagePath())
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 20))
                .into(imageView);

//        ImgLoadUtils.loadCircularBead(context, icon_banner.getImagePath(), imageView, 10);
//        tvBannerTitle.setText(icon_banner.getTitle());
    }
}
