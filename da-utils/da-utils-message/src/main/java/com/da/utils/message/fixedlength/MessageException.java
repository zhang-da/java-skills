package com.da.utils.message.fixedlength;

public class MessageException extends RuntimeException {
    private String message;

    public MessageException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
