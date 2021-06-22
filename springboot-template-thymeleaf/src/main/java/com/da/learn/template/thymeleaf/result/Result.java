package com.da.learn.template.thymeleaf.result;

import java.io.Serializable;


public class Result<T> implements Serializable {



    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

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

    public static <T> Result<T> ok() {
        return restResult(null, ResultConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, ResultConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, ResultConstants.SUCCESS, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(null, ResultConstants.FAIL, null);
    }

    public static <T> Result<T> failed(int code) {
        return restResult(null, code, null);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, ResultConstants.FAIL, msg);
    }

    public static <T> Result<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
