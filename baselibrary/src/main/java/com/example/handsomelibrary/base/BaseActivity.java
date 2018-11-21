package com.example.handsomelibrary.base;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.handsomelibrary.utils.NetWorkChangReceiver;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity 基类
 * Created by Stefan on 2018/4/20 15:07.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    //    protected ACache mCache;
    public Dialog loading_dialog;
    protected BaseActivity mContext;
    protected KProgressHUD mKProgressHUD;
    private boolean isRegistered = false;
    private NetWorkChangReceiver netWorkChangReceiver;

    /**
     * 获取TAG的activity名称
     */
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 是否显示标题栏
     */
    private boolean isShowTitle = true;

    /**
     * 是否显示标题栏
     */
    private boolean isShowState = true;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loading_dialog = new AlertDialog.Builder(this).setMessage("loading...").create();
        mContext = this;
        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
//        if (isShowState) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        mCache = ACache.get(this);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        BaseInitView();
        initData(savedInstanceState);

        //注册网络状态监听广播
        netWorkChangReceiver = new NetWorkChangReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
        isRegistered = true;
    }

    @Override
    public void onClick(View view) {
    }

    /**
     * 是否设置标题栏
     *
     * @return
     */
    public void setTitle(boolean ishow) {
        isShowTitle = ishow;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param ishow
     */
    public void setState(boolean ishow) {
        isShowState = ishow;
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化布局
     */
    protected void BaseInitView() {
    }

    /**
     * 设置数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        //解绑
        if (isRegistered) {
            unregisterReceiver(netWorkChangReceiver);
        }

        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }
}
