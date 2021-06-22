package com.da.learn.template.thymeleaf.exception;

public class AirException extends RuntimeException {
    private static final long serialVersionUID = 632278832367288851L;

    private Integer code;

    private String msg;

    public AirException(String message) {
        super(message);
        this.code = 500;
        this.msg = message;
    }

    public AirException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public AirException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
