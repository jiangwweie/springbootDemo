package com.jiangwei.sg.entity;

public class ResponseBean {


     static final int SUCCESS_CODE = 1 ;

     static final int ERROR_CODE = 2 ;



    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public ResponseBean() {
    }

    public ResponseBean(int code, String msg, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseBean success(Object data,String msg){
        this.setCode(SUCCESS_CODE);
        this.setData(data);
        this.setMsg(msg);
        return  this ;
    }
}