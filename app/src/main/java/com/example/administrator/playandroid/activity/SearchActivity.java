package com.example.administrator.playandroid.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.playandroid.R;
import com.example.handsomelibrary.api.ApiService;
import com.example.handsomelibrary.base.BaseActivity;
import com.example.handsomelibrary.interceptor.Transformer;
import com.example.handsomelibrary.model.ProjectChildBean;
import com.example.handsomelibrary.model.SearchBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;
import com.example.handsomelibrary.retrofit.observer.CommonObserver;
import com.example.handsomelibrary.utils.JumpUtils;
import com.example.handsomelibrary.utils.KeyBoardUtils;
import com.example.handsomelibrary.utils.L;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 * Created by Stefan on 2018/12/7 11:12
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.fl_tags)
    TagFlowLayout fl_tags;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search)
    ImageView iv_search;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        et_search.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
////                getConfirmOrder(mPageNo, et_search_heat.getText().toString());
////                mKProgressHUD.dismiss();
////                initView();
//                getQueryList(0, et_search.getText().toString());
//                KeyBoardUtils.closeKeyBoard(mContext);
//                return true;
//            }
//            return false;
//        });
    }

//    private void setTagFlow() {
//        fl_tags.setAdapter(new TagAdapter<String>(setTagData()) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                View inflate = LayoutInflater.from(mContext).inflate(R.layout.tags_tv, fl_tags, false);
//                TextView tv_tags = inflate.findViewById(R.id.tv_tags);
//                tv_tags.setText(s);
//                return inflate;
//            }
//        });
//        fl_tags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//               // setHotSearchResult(position);
//                return true;
//            }
//        });
//    }

    private void getQueryList(int page, String searchText) {
        RxHttpUtils.createApi(ApiService.class)
                .getQueryList(page, searchText)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<SearchBean>() {
                    @Override
                    protected void onSuccess(SearchBean searchBean) {
                        L.d("");
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                JumpUtils.exitActivity(mContext);
                break;
            case R.id.iv_search:
                getQueryList(0,et_search.getText().toString());
                break;
        }
    }

}
