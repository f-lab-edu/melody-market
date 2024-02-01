package com.melodymarket.infrastructure.exception;

public class DataNotFoundException extends NullPointerException {

    public DataNotFoundException(String msg) {
        super(msg);
    }
}
