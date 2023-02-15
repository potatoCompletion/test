package com.temp.api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.api.common.dto.CommonResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 로그인 실패 핸들링 메서드
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        CommonResponse failResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.LOGIN_FAILED.getCode())
                .errorMessage(getExceptionMessage(exception))
                .build();

        // json 으로 에러 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(failResponse));
    }

    /**
     * 로그인 에러메세지 파싱
     * @param exception
     * @return String
     */
    private String getExceptionMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "비밀번호불일치";
        } else if (exception instanceof UsernameNotFoundException) {
            return "계정없음";
        } else {
            return "미확인 에러";
        }
    }
}
