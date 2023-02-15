package com.temp.api.user.domain;

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
@Table(name="ORDERS")
public class OrdersEntity extends BaseTimeEntity {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Long orderCode;

    @Column(name = "request_user_code", nullable = false)
    private Long requestUserCode;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "rider_user_code")
    private Long riderUserCode;

    @Column(name = "is_updatable", nullable = false, columnDefinition = "TINYINT(1)", length = 1)
    private boolean isUpdatable;

    @Column(name = "is_completion", nullable = false, columnDefinition = "TINYINT(1)", length = 1)
    private boolean isCompletion;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    // constructor
    @Builder
    public OrdersEntity(Long requestUserCode, String toAddress) {

        validate(requestUserCode, toAddress);

        this.requestUserCode = requestUserCode;
        this.toAddress = toAddress;
        this.isUpdatable = true;    // default
        this.isCompletion = false;  // default
    }

    public void changeToAddress(String toAddress) { this.toAddress = toAddress; }

    // 라이더가 주문을 받았을 때 업데이트 용도
    public void changeRiderUserCode(Long riderUserCode) {
        this.riderUserCode = riderUserCode;
    }

    // 배달이 시작됐을 때 (라이더가 배달품목을 픽업 했을 때) 업데이트 용도
    public void changeIsUpdatable(Boolean isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    // 배달이 완료 되었을 때 업데이트 용도
    public void changeIsCompletion(Boolean isCompletion) { this.isCompletion = isCompletion; }

    private void validate(Long requestUserCode, String toAddress) {
        // 안전한 객체 생성을 위한 검증 (빈 값이 들어올 시 에러내기 위해서)
        Assert.notNull(requestUserCode, "userId must not be null!");
        Assert.hasText(toAddress, "name must not be empty!");
    }

}