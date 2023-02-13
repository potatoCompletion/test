package com.temp.api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserService userService;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 최근 로그인 일자 업데이트
        var user = authentication.getPrincipal();
        userService.updateLastLogin(((UserDetails) user).getUsername());

        SavedRequest savedRequest = requestCache.getRequest(request, response);  // 클라이언트가 원래 접근하려던 url 확인 용도
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var tokenInfo = jwtTokenProvider.generateToken(authentication);

        // json 형식으로 토큰 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tokenInfo));

        /**
         * TODO: refresh token redis 저장 로직
         */

        if (savedRequest != null) {  // 클라이언트가 원래 접근하려던 url이 있을 경우
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }
    }
}
