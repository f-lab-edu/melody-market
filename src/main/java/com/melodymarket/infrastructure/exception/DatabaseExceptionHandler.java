package com.melodymarket.infrastructure.exception;

import com.melodymarket.common.dto.ResponseDto;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {
    @ExceptionHandler(DataDuplicateKeyException.class)
    public ResponseDto<Object> handleDuplicateKeyException(DataDuplicateKeyException ex) {
        return ResponseDto.of(HttpStatus.CONFLICT,
                ex.getMessage(),
                "");

    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseDto<Object> handleNotFoundException(DataNotFoundException ex) {
        return ResponseDto.of(HttpStatus.NOT_FOUND,
                ex.getMessage(),
                "");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseDto<Object> handleDataAccessException(DataAccessException ex) {
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                "");
    }
}
