package com.melodymarket.infrastructure.exception;

import org.springframework.dao.DataAccessException;

public class DataAccessCustomException extends DataAccessException {
    public DataAccessCustomException(String msg) {
        super(msg);
    }
}
