package com.temp.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.temp.api.common.enums.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonSuccessResponse {

    @JsonProperty("status")     // 응답코드
    private HttpStatus status;
    @JsonProperty("message")    // 응답메세지
    private ResponseMessage message;
    @JsonProperty("data")       // 응답데이터
    private Object data;
}
