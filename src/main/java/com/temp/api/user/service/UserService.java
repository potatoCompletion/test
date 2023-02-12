package com.temp.api.user.service;

import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoEntity join(JoinParam joinParam) {

        if (userRepository.existsByUserId(joinParam.getUserId())) {
            throw new InvalidParameterException("이미 존재하는 아이디 입니다.");
        }

        UserInfoEntity newUser = UserInfoEntity.builder()
                .userId(joinParam.getUserId())
                .password(joinParam.getPassword())
                .name(joinParam.getUserName())
                .role(joinParam.getRole())
                .build();

        return userRepository.save(newUser);
    }
}
