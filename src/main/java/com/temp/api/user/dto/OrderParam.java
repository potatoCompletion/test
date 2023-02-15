package com.temp.api.user.dto;

import lombok.*;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderParam {
    private String toAddress;
}
