package com.temp.api.common.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    // 사용할 Entity에 상속해서 사용
    @CreatedDate
    private LocalDateTime createdDate;  // 생성일자 자동화

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정일자 자동화
}