package com.example.administrator.playandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.model.NavigationBean;

import java.util.List;

/**
 * Created by Stefan on 2018/12/11 10:14
 */
public class NavigationLeftAdapter extends BaseQuickAdapter<NavigationBean, BaseViewHolder> {
    int checkPosition = 0;

    public int getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    public NavigationLeftAdapter(@Nullable List<NavigationBean> data) {
        super(R.layout.item_navigation_left, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationBean item) {
        RadioButton rb_left = helper.getView(R.id.rb_left);
        helper.addOnClickListener(R.id.rb_left);
        rb_left.setText(item.getName());
        if (helper.getLayoutPosition() == getCheckPosition()) {
            rb_left.setChecked(true);
            rb_left.setTextColor(mContext.getResources().getColor(R.color.label));
        } else {
            rb_left.setChecked(false);
            rb_left.setTextColor(mContext.getResources().getColor(R.color.txtSuperColor));
        }
    }
}
