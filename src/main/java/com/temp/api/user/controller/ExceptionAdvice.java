package com.temp.api.user.controller;

import com.sun.jdi.request.DuplicateRequestException;
import com.temp.api.common.dto.CommonFailResponse;
import com.temp.api.common.exception.ErrorCode;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {
    /**
     * 파라미터 검증 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonFailResponse> invalidParam(MethodArgumentNotValidException ex) {
        var detailMessageArgs = ex.getDetailMessageArguments();
        var paramErrorMessage = Arrays.stream(detailMessageArgs).toList().get(1).toString();

        // 에러메세지 파싱 결과 비어있을 경우 기본메세지 설정
        if(paramErrorMessage.isBlank()) {
            paramErrorMessage = ErrorCode.INVALID_PARAM.getMessage();
        }

        CommonFailResponse response = CommonFailResponse.builder()
                .code(ErrorCode.INVALID_PARAM.getCode())
                .message(paramErrorMessage)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 아이디 중복 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(DuplicateRequestException.class)
    protected ResponseEntity<CommonFailResponse> duplicateId() {

        CommonFailResponse response = CommonFailResponse.builder()
                .code(ErrorCode.DUPLICATE_USERID.getCode())
                .message(ErrorCode.DUPLICATE_USERID.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 데이터 미존재 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<CommonFailResponse> dataNotFound() {

        CommonFailResponse response = CommonFailResponse.builder()
                .code(ErrorCode.DATA_NOT_FOUND.getCode())
                .message(ErrorCode.DATA_NOT_FOUND.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * DB 접근 중 오류
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<CommonFailResponse> dataAccessError() {

        CommonFailResponse response = CommonFailResponse.builder()
                .code(ErrorCode.DATA_ACCESS_ERROR.getCode())
                .message(ErrorCode.DATA_ACCESS_ERROR.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }
}