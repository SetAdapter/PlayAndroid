package com.example.handsomelibrary.model;

import java.io.Serializable;

/**
 * 网络请求 返回数据 格式化对象
 * Created by fangs on 2017/11/6.
 */
public class BaseBean<T> implements Serializable {

    /**
     * 所有的返回结构均为上述，其中errorCode如果为负数则认为错误，此时errorMsg会包含错误信息。data为Object，返回数据根据不同的接口而变化。
     * 1. errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
     * 2. errorCode = -1001 代表登录失效，需要重新登录。
     */

    /**
     * data : ...
     * errorCode : 0
     * errorMsg :
     */

    private T data;
    private int errorCode;
    private String errorMsg;


    public boolean isSuccess(){
        return errorCode == 0 ? true : false;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "data='" + data + '\'' +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
