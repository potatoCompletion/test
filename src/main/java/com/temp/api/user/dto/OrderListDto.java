package com.temp.api.user.dto;

import com.temp.api.common.enums.Roles;
import lombok.*;

import java.time.LocalDate;

@Data
@Setter(AccessLevel.NONE)
public class OrderListDto {
    private Long userCode;
    private Roles role;
    private int period;

    @Builder
    public OrderListDto(Long userCode, Roles role, int period) {
        this.userCode = userCode;
        this.role = role;
        this.period = period;
    }
}
