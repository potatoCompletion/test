package com.temp.api.user.domain;

import com.temp.api.common.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserInfoTest {

    @Test
    public void null객체생성테스트() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
        });
    }

    @Test
    public void empty객체생성테스트() {

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("")
                .userPassword("")
                .userName("")
                .role(Roles.USER)
                .build());
    }

    @Test
    public void 정상객체생성테스트() {

        Assertions.assertThrows(RuntimeException.class, () -> UserInfoEntity.builder()
                .userId("testId")
                .userPassword("testPassword")
                .userName("하지원")
                .role(Roles.USER)
                .build());

//        Assertions.assertEquals();
    }
}
