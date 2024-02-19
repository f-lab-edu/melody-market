package com.melodymarket.common.exception;

import com.melodymarket.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 유효성 검사에 문제가 있을 경우 badRequest
     *
     * @param ex Validation 검사 예외
     * @return badRequest error message return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors
                        .toMap(FieldError::getField
                                , fieldError -> Optional
                                        .ofNullable(fieldError.getDefaultMessage())
                                        .orElse(" ")));

        return ResponseDto.of(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다.", errors);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseDto<String> handlePasswordMissMatchException(PasswordMismatchException ex) {

        return ResponseDto.of(HttpStatus.NOT_MODIFIED, ex.getMessage(), null);
    }
}
