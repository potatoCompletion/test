package com.temp.api.common.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
