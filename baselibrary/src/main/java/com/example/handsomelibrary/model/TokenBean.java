package com.example.handsomelibrary.model;

/**
 * Created by QKun on 2018/7/13.
 */

public class TokenBean {

    /**
     * msg : token失效，请重新登录
     * code : 401
     */

    private String msg;
    private int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
