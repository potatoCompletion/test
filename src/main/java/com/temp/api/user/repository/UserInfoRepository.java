package com.temp.api.user.repository;

import com.temp.api.user.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    UserInfoEntity save(UserInfoEntity userInfo);
    boolean existsByUserId(String userId);  // 아이디 중복체크
    Optional<UserInfoEntity> findByUserCode(Long userCode);
    Optional<UserInfoEntity> findByUserId(String userId);
    Optional<UserInfoEntity> findByName(String name);
}
