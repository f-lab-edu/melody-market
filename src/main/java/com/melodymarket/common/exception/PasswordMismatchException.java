package com.melodymarket.common.exception;

public class PasswordMismatchException extends IllegalStateException {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
