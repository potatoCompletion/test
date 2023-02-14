package com.temp.api;

import com.temp.api.common.security.JwtAuthenticationFilter;
import com.temp.api.common.security.LoginFailureHandler;
import com.temp.api.common.security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()  // csrf 정책 미적용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 사용 비활성화 (토큰 사용하기 때문)
                    .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/join").permitAll()  // 권한없이 접근가능한 url
                .anyRequest().authenticated()  // 모든 요청에 대해 인증된 사용자만 가능
                    .and()
                .formLogin()
                .successHandler(loginSuccessHandler)  // 로그인 성공 핸들러
                .failureHandler(loginFailureHandler)  // 로그인 실패 핸들러
                .usernameParameter("userId")  // 넘겨받는 로그인 파라미터명 커스텀 (기본 username)
                .passwordParameter("password")
                .loginProcessingUrl("/login")  // 로그인 프로세스 url (직접 구현 x, 내부 생성)
                .permitAll()
                    .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // securityContext 이용하기 위함. 없을 시 토큰 발급 기록불가

        return http.build();
    }
}