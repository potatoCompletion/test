package com.temp.api.user.service;

import com.temp.api.common.security.PasswordEncoder;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserInfoEntity join(JoinParam joinParam) {

        // 아이디 중복 검증
        if (userRepository.existsByUserId(joinParam.getUserId())) {
            throw new InvalidParameterException("이미 존재하는 아이디 입니다.");
        }

        UserInfoEntity newUser = UserInfoEntity.builder()
                .userId(joinParam.getUserId())
                .password(passwordEncoder.encode(joinParam.getPassword()))  // 비밀번호 암호화
                .name(joinParam.getUserName())
                .role(joinParam.getRole())
                .build();

        return userRepository.save(newUser);
    }

    public void updateLastLogin(String userId) {
        UserInfoEntity user = userRepository.findByUserId(userId)
                .orElseThrow();

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);
    }
}
