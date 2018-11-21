package com.example.handsomelibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.TextView;

import com.example.handsomelibrary.application.BaseApplication;


/**
 * Tint 是 Android5.0 引入的一个属性
 * Created by fangs on 2018/2/11.
 */
public class TintUtils {

    /**
     * tint这个属性，是ImageView有的，它可以给ImageView的src设置，除了tint 之外，
     * 还有backgroundTint,foregroundTint,drawableTint,它们分别对应对背景、前景、drawable进行着色处理。
     */
    private TintUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /****************优雅的实现背景选择器*******************/
    /*
        1、显示不同的颜色 数组
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.pink),
            ContextCompat.getColor(this, R.color.colorPrimaryDark)};
       /2、View状态数组（比如按下，选中等）
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_pressed};
            states[1] = new int[]{};

        方式一：
            Drawable drawable2 = tintSelector(drawable, colors, states);
            imageView1.setBackground(drawable2);
        方式二：
            StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
            Drawable drawable3 = tintSelector(stateListDrawable, colors, states);
            imageView2.setBackground(drawable3);
     */

    /**
     * StateListDrawable 设置背景选择器
     * @param drawable              图片资源（shape,png图片，svg图）
     * @param states                View状态数组（比如按下，选中等）
     * @return
     */
    public static StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state : states) {
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }

    /**
     * Tint 方式实现 selector
     * @param drawable          图片资源（shape, png图片，svg）
     * @param colors            不同状态 显示不同的颜色 数组
     * @param states            View状态数组（比如按下，选中等）
     * @return
     */
    public static Drawable tintSelector(Drawable drawable, int[] colors, int[][] states) {
        ColorStateList colorList = new ColorStateList(states, colors);

        Drawable.ConstantState state = drawable.getConstantState();
        drawable = DrawableCompat
                .wrap(state == null ? drawable : state.newDrawable())
                .mutate();

        DrawableCompat.setTintList(drawable, colorList);

        return drawable;
    }


    /**
     * 设置icon 在TextView的位置
     * @param tv
     * @param drawable
     * @param position 0、1、2、3 分别对应：左、上、右、下
     */
    public static void setTxtIconLocal(TextView tv, Drawable drawable, int position){
        drawable.setBounds(0, 0,
                drawable.getMinimumWidth(), drawable.getMinimumHeight());

        switch (position) {
            case 0:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 1:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }
}
