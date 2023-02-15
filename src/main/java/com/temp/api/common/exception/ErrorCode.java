package com.temp.api.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorCode {

    // 파라미터 에러
    INVALID_PARAM("1000", "파라미터에 오류가 존재합니다."),
    DUPLICATE_USERID("1001", "유저 아이디가 중복됩니다."),

    // 데이터 에러
    DATA_NOT_FOUND("2000", "데이터가 존재하지 않습니다."),
    DATA_ACCESS_ERROR("2001", "DB 접근 중 에러가 발생했습니다."),
    DATA_CANNOT_UPDATE("2002", "업데이트에 실패했습니다."),

    // 로그인 에러
    LOGIN_FAILED("3000", "로그인 정보가 유효하지 않습니다."),

    // 토큰 에러
    TOKEN_ERROR("4000", "토큰 정보가 올바르지 않습니다."),
    ;

    @JsonProperty("code")
    private final String code;
    @JsonProperty("message")
    private final String message;
}