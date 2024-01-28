package com.melodymarket.infrastructure.mybatis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MybatisExceptionHandler {
    @ExceptionHandler(MybatisDuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplecateKeyException(MybatisDuplicateKeyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());

    }
}
