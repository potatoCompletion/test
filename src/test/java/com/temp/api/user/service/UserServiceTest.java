package com.temp.api.user.service;

import com.temp.api.common.enums.Roles;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Test
    void 아이디중복테스트() {
        //given
//        JoinParam joinParam = new JoinParam("test123", "qqqqqqqqqQQQQQ!!!!!!!!!", "김완수", Roles.USER);
//        UserInfoEntity user = UserInfoEntity.builder()
//                .userId(joinParam.getUserId())
//                .password(joinParam.getPassword())
//                .name(joinParam.getUserName())
//                .role(joinParam.getRole())
//                .build();
//
//        //mocking
//        given(userInfoRepository.save(any()))
//                .willReturn(user);
    }
}
