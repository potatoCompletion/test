package com.temp.api.user.domain;

import com.temp.api.common.Roles;
import com.temp.api.common.domain.BaseTimeEntity;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="USERINFO")
public class UserInfoEntity extends BaseTimeEntity {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // constructor
    @Builder
    public UserInfoEntity(String userId, String password, String name, Roles role) {

        validate();

        this.userId = userId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    private void validate() {
        // 안전한 객체 생성을 위한 검증 (빈 값이 들어올 시 에러내기 위해서)
        Assert.hasText(userId, "userId must not be empty!");
        Assert.hasText(password, "userPassword must not be empty!");
        Assert.hasText(password, "userName must not be empty!");
    }

}
