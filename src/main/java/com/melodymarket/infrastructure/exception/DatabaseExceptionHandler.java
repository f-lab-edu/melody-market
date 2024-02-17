package com.melodymarket.infrastructure.exception;

import com.melodymarket.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {
    @ExceptionHandler(DataDuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicateKeyException(DataDuplicateKeyException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());

    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(DataNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());

    }

    @ExceptionHandler(DataAccessCustomException.class)
    public ResponseDto<Object> handleDataAccessException(DataAccessCustomException ex) {
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                "");
    }
}
