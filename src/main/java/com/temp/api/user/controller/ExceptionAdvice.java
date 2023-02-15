package com.temp.api.user.controller;

import com.sun.jdi.request.DuplicateRequestException;
import com.temp.api.common.dto.CommonResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.common.exception.ErrorCode;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {
    /**
     * 파라미터 검증 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponse> invalidParam(MethodArgumentNotValidException ex) {
        var detailMessageArgs = ex.getDetailMessageArguments();
        var parsedMessage = Arrays.stream(detailMessageArgs).toList().get(1).toString();
        String exceptionMessage = getExceptionMessage(parsedMessage, ErrorCode.INVALID_PARAM.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.INVALID_PARAM.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    /**
     * 아이디 중복 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(DuplicateRequestException.class)
    protected ResponseEntity<CommonResponse> duplicateId(Exception ex) {
        String exceptionMessage = getExceptionMessage(ex.getMessage(), ErrorCode.DUPLICATE_USERID.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.DUPLICATE_USERID.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    /**
     * 데이터 미존재 오류
     * @return ResponseEntity<CommonFailResponse>
     */
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<CommonResponse> dataNotFound(Exception ex) {
        String exceptionMessage = getExceptionMessage(ex.getMessage(), ErrorCode.DATA_NOT_FOUND.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.DATA_NOT_FOUND.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    /**
     * DB 접근 중 오류
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<CommonResponse> dataAccessError(Exception ex) {
        String exceptionMessage = getExceptionMessage(ex.getMessage(), ErrorCode.DATA_ACCESS_ERROR.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.DATA_ACCESS_ERROR.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    /**
     * GET Query String으로 전달받은 파라미터 오류
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<CommonResponse> invalidQueryString(Exception ex) {
        String exceptionMessage = getExceptionMessage(ex.getMessage(), ErrorCode.INVALID_PARAM.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.INVALID_PARAM.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    @ExceptionHandler(IncorrectUpdateSemanticsDataAccessException.class)
    protected ResponseEntity<CommonResponse> cannotUpdate(Exception ex) {

        String exceptionMessage = getExceptionMessage(ex.getMessage(), ErrorCode.DATA_CANNOT_UPDATE.getMessage());

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.DATA_ACCESS_ERROR.getCode())
                .errorMessage(exceptionMessage)
                .build();

        return ResponseEntity.ok(failResponse);
    }

    private String getExceptionMessage(String exceptionMessage, String defaultMessage) {
        // 에러메세지 파싱 결과 비어있을 경우 기본메세지 설정
        return exceptionMessage.isBlank() ? defaultMessage : exceptionMessage;
    }
}