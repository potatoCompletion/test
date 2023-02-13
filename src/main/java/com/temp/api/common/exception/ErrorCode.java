package com.temp.api.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorCode {

    // 파라미터 검증 에러
    INVALID_PARAM("1000", "파라미터에 오류가 존재합니다."),
    DUPLICATE_USERID("1001", "유저 아이디가 중복됩니다."),

    // 데이터 조회 에러
    DATA_NOT_FOUND("2000", "데이터가 존재하지 않습니다."),

    // 로그인 에러
    LOGIN_FAILED("3000", "로그인 정보가 유효하지 않습니다."),
    ;

    @JsonProperty("code")
    private final String code;
    @JsonProperty("message")
    private final String message;
}