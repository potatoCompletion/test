package com.temp.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonFailResponse {

    @JsonProperty("errorCode")     // 응답코드
    private String code;
    @JsonProperty("message")    // 응답메세지
    private String message;
}