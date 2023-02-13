package com.temp.api.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class OrderListParam {
    private LocalDate period;
}
