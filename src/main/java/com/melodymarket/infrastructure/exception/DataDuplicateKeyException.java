package com.melodymarket.infrastructure.exception;

import org.springframework.dao.DuplicateKeyException;

public class DataDuplicateKeyException extends DuplicateKeyException {

    public DataDuplicateKeyException(String msg) {
        super(msg);
    }
}
