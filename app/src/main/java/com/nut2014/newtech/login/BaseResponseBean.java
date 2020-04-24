package com.nut2014.newtech.login;

public class BaseResponseBean<T> {

    public BaseResponseBean() {
    }

    public BaseResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private int code;
    private String msg;
    private T data;

    public T getData() {
        return data;
    }

    public BaseResponseBean<T> setData(T data) {
        this.data = data;
        return this;
    }
}
