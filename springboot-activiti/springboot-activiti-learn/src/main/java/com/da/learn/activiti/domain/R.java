package com.da.learn.activiti.domain;

public class R<T> {
    private Integer code;
    private String message;
    private T t;

    public static R<?> success() {
        return new R<>(0, "success");
    }

    public static <T> R<T> success(T t) {
        return new R<>(0, "success", t);
    }

    public static R<?> failure(String message) {
        return new R<>(1, message);
    }

    public R() {
    }

    public R(Integer code) {
        this.code = code;
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(Integer code, String message, T t) {
        this.code = code;
        this.message = message;
        this.t = t;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
