package com.example.handsomelibrary.retrofit.observer;

import android.app.Dialog;

import com.example.handsomelibrary.model.BaseBean;
import com.example.handsomelibrary.retrofit.RxHttpUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by Allen on 2017/10/31.
 *
 * @author Allen
 *         <p>
 *         针对特定格式的时候设置的通用的Observer
 *         用户可以根据自己需求自定义自己的类继承BaseDataObserver<T>即可
 *         适用于
 *         {
 *         "code":200,
 *         "msg":"成功"
 *         "data":{
 *         "userName":"test"
 *         "token":"abcdefg123456789"
 *         "uid":"1"}
 *         }
 */

public abstract class DataObserver<T> extends BaseDataObserver<T> {

    private Dialog mProgressDialog;

    public DataObserver() {
    }

    public DataObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }


    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    @Override
    public void doOnSubscribe(Disposable d) {
        RxHttpUtils.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        if (!isHideToast()) {
            //T.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(BaseBean<T> data) {
        onSuccess(data.getRows());
        //可以根据需求对code统一处理
//        switch (data.getCode()) {
//            case 200:
//                onSuccess(data.getData());
//                break;
//            case 300:
//            case 500:
//                onError(data.getMsg());
//                break;
//            default:
//        }
    }

    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
