package com.example.administrator.playandroid.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.handsomelibrary.model.NavigationBean;

/**
 * Created by Stefan on 2018/12/11 16:15
 */
public class NavigationBeanSection extends SectionEntity<NavigationBean.ArticlesBean> {
    public NavigationBeanSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public NavigationBeanSection(NavigationBean.ArticlesBean navigationBean) {
        super(navigationBean);
    }
}
