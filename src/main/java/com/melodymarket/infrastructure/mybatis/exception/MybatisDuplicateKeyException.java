package com.melodymarket.infrastructure.mybatis.exception;

import org.springframework.dao.DuplicateKeyException;

public class MybatisDuplicateKeyException extends DuplicateKeyException {

    public MybatisDuplicateKeyException(String msg) {
        super(msg);
    }
}
