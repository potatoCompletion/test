package com.temp.api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.api.common.dto.CommonResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.common.exception.ErrorCode;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    // JWT Exception 핸들링

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (SecurityException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }
    }

    /**
     * JWT error response 설정 메서드
     * @param status
     * @param response
     * @param ex
     */
    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {

        CommonResponse failResponse = CommonResponse.builder()
                .status(status)
                .message(ResponseMessage.FAIL)
                .errorCode(ErrorCode.TOKEN_ERROR.getCode())
                .errorMessage(ex.getMessage())
                .build();

        // json 으로 에러 반환
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(failResponse));
    }
}
