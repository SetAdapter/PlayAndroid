package com.example.administrator.playandroid.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Stefan on 2018/12/11 10:14
 */
public class NavigationRightAdapter extends BaseSectionQuickAdapter<NavigationBeanSection, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NavigationRightAdapter(List<NavigationBeanSection> data) {
        super(R.layout.item_navigation_right, R.layout.item_header_navigation_right, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, NavigationBeanSection item) {
        helper.setText(R.id.tv_header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationBeanSection item) {
        TextView tv_label = helper.getView(R.id.tv_label);
        tv_label.setText(item.t.getTitle());
        //随机颜色
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        helper.setTextColor(R.id.tv_label, ranColor);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(5);
        
        drawable.setStroke(1, Color.parseColor("#cccccc"));
        drawable.setColor(Color.parseColor("#eeeeee"));
        tv_label.setBackgroundDrawable(drawable);

    }
}
