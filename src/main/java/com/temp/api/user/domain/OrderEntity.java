package com.temp.api.user.domain;

import com.temp.api.common.enums.Roles;
import com.temp.api.common.domain.BaseTimeEntity;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="ORDER")
public class OrderEntity extends BaseTimeEntity {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Long orderCode;

    @Column(name = "request_user_code", nullable = false)
    private Long requestUserCode;

    @Column(name = "request_user_name", nullable = false, length = 60)
    private String requestUserName;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "rider_user_code")
    @Enumerated(EnumType.STRING)
    private Long riderUserCode;

    @Column(name = "is_updatable", nullable = false)
    private boolean isUpdatable;

    // constructor
    @Builder
    public OrderEntity(Long requestUserCode, String requestUserName, String toAddress, Boolean isUpdatable) {

        validate(requestUserCode, requestUserName, toAddress, isUpdatable);

        this.requestUserCode = requestUserCode;
        this.requestUserName = requestUserName;
        this.toAddress = toAddress;
        this.isUpdatable = isUpdatable;
    }

    public void setRiderUserCode(Long riderUserCode) {
        this.riderUserCode = riderUserCode;
    }

    public void setIsUpdatable(Boolean isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    private void validate(Long requestUserCode, String requestUserName, String toAddress, Boolean isUpdatable) {
        // 안전한 객체 생성을 위한 검증 (빈 값이 들어올 시 에러내기 위해서)
        Assert.notNull(requestUserCode, "userId must not be null!");
        Assert.hasText(requestUserName, "userPassword must not be empty!");
        Assert.hasText(toAddress, "name must not be empty!");
        Assert.notNull(isUpdatable, "role must not be null!");
    }

}