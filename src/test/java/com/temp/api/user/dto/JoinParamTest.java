package com.temp.api.user.dto;

import com.temp.api.common.enums.Roles;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;

public class JoinParamTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    @Test
    public void 비밀번호정규식테스트() {
        //given
        JoinParam user = new JoinParam("", "abcdefghijklM!", "하지원", Roles.USER);

        //when
//        Set<ConstraintViolation<UserParam>> violationSet = validator.validate(user, );

        //then
    }
}
