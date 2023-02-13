package com.temp.api.user.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.temp.api.common.enums.Roles;
import com.temp.api.user.domain.OrderEntity;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.repository.OrderRepository;
import com.temp.api.user.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final OrderRepository orderRepository;

    /**
     *  유저 회원가입(저장)
     * @param joinParam
     * @return UserInfoEntity
     */
    public UserInfoEntity join(JoinParam joinParam) {

        // 아이디 중복 검증
        if (userInfoRepository.existsByUserId(joinParam.getUserId())) {
            throw new DuplicateRequestException("이미 존재하는 아이디 입니다.");
        }

        UserInfoEntity newUser = UserInfoEntity.builder()
                .userId(joinParam.getUserId())
                .password(passwordEncoder.encode(joinParam.getPassword()))  // 비밀번호 암호화
                .name(joinParam.getUserName())
                .role(joinParam.getRole())
                .build();

        return userInfoRepository.save(newUser);
    }

    /**
     * 최근 로그인 업데이트
     * @param userId
     */
    public void updateLastLogin(String userId) {
        UserInfoEntity user = userInfoRepository.findByUserId(userId)
                .orElseThrow();

        user.setLastLogin(LocalDateTime.now());

        userInfoRepository.save(user);
    }

    /**
     *
     * @param name
     * @return Optional<List<OrderEntity>>
     */
    public Optional<List<OrderEntity>> selectOrderList(String name) {
        UserInfoEntity user = userInfoRepository.findByName(name)
                .orElseThrow();

        if (user.getRole().equals(Roles.RIDER)) {   // user.role 이 rider 일 경우
            return orderRepository.findAllByRiderUserCode(user.getUserCode());
        }

        return orderRepository.findAllByRequestUserCode(user.getUserCode());
    }
}
