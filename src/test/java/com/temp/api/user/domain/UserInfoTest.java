package com.temp.api.user.domain;

import com.temp.api.common.enums.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserInfoTest {

    @Test
    public void 이상객체생성테스트() {

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("")
                .password("qqqqqqqqqqqQQQQQ!!!!!!!!!!!")
                .name("하지원")
                .role(Roles.USER)
                .build());

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("test2")
                .password("")
                .name("하지원")
                .role(Roles.USER)
                .build());

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("test3")
                .password("qqqqqqqqqqqQQQQQ!!!!!!!!!!!")
                .name("")
                .role(Roles.USER)
                .build());

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("test3")
                .password("qqqqqqqqqqqQQQQQ!!!!!!!!!!!")
                .name("하지원")
                .build());
    }

    @Test
    public void 정상객체생성테스트() {

        Assertions.assertDoesNotThrow(() -> UserInfoEntity.builder()
                .userId("testId")
                .password("testPassword")
                .name("하지원")
                .role(Roles.USER)
                .build());
    }
}
