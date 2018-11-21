package com.example.handsomelibrary.model;

import java.io.Serializable;

/**
 * 网络请求 返回数据 格式化对象
 * Created by fangs on 2017/11/6.
 */
public class BaseBean<T> implements Serializable {
    /**
     * msg : 操作成功！
     * code : 0
     * rows : {}
     */

    private String msg;
    private int code;
    private T rows;

    public boolean isSuccess(){
        return code == 0 ? true : false;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public static class RowsBean {
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", rows=" + rows +
                '}';
    }
}
