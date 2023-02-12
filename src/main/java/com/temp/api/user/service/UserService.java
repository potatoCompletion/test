package com.temp.api.user.service;

import com.temp.api.common.Roles;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.UserParam;
import com.temp.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(UserParam userParam) {

        UserInfoEntity newUser = UserInfoEntity.builder()
                .userId(userParam.getUserId())
                .password(userParam.getPassword())
                .name(userParam.getUserName())
                .role(userParam.getRole())
                .build();

        userRepository.save(newUser);

        return newUser.getId();
    }
}
