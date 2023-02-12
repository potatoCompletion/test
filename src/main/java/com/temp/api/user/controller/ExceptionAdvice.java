package com.temp.api.user.controller;

import com.temp.api.common.dto.CommonFailResponse;
import com.temp.api.common.dto.CommonSuccessResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonFailResponse> invalidParam() {

        CommonFailResponse response = CommonFailResponse.builder()
                .code(UserException.INVALID_PARAM.getCode())
                .message(UserException.INVALID_PARAM.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<CommonFailResponse> duplicateId() {

        CommonFailResponse response = CommonFailResponse.builder()
                .code(UserException.DUPLICATE_USERID.getCode())
                .message(UserException.DUPLICATE_USERID.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }
}