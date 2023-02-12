package com.temp.api.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseMessage {
    SUCCESS("성공"),
    FAIL("오류 발생"),
    ;
    private final String message;
}
