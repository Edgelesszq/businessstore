package com.businessstore.model;

public class Json<T>{

    private int code;
    private String msg;
    private String debugmsg;
    private String url;
    private T data;

    public String getDebugmsg() {
        return debugmsg;
    }

    public void setDebugmsg(String debugmsg) {
        this.debugmsg = debugmsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
