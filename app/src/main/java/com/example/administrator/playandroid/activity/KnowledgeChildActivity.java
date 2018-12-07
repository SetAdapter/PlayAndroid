package com.example.administrator.playandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.playandroid.R;
import com.example.administrator.playandroid.fragment.KnowledgeChildFragment;
import com.example.handsomelibrary.base.BaseActivity;
import com.example.handsomelibrary.model.KnowledgeBean;
import com.example.handsomelibrary.utils.JumpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 知识体系下 二级界面
 * Created by Stefan on 2018/11/27 15:15
 */

public class KnowledgeChildActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected int getContentView() {
        return R.layout.activity_knowledge_child;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        KnowledgeBean knowledgeBean = (KnowledgeBean) bundle.getSerializable("knowledgeBean");
        if(knowledgeBean!=null){
            List<KnowledgeBean.ChildrenBean> beanChildren = knowledgeBean.getChildren();
            tv_title.setText(knowledgeBean.getName());
            //设置ViewPager
            mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return KnowledgeChildFragment.getInstance();
                }

                @Override
                public int getCount() {
                    return beanChildren.size();
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return beanChildren.get(position).getName();
                }
            });
            mViewPager.setOffscreenPageLimit(4);
            mViewPager.addOnPageChangeListener(this);
            mTabLayout.setupWithViewPager(mViewPager);
            //TabLayout的Tab选择监听
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //发送事件数据（分类的id）至 KnowledgeChildFragment
                    EventBus.getDefault().post(new AttentionEvent(beanChildren.get(tab.getPosition()).getId()));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    /**
     * ViewPager回调事件
     * */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0 || positionOffsetPixels == 0) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                JumpUtils.exitActivity(mContext);
                break;
        }
    }
}
