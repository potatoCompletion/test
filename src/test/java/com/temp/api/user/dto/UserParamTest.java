package com.temp.api.user.dto;

import com.temp.api.common.Roles;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class UserParamTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    @Test
    public void 비밀번호정규식테스트() {
        //given
        UserParam user = new UserParam("", "abcdefghijklM!", "하지원", Roles.USER);

        //when
//        Set<ConstraintViolation<UserParam>> violationSet = validator.validate(user, );

        //then
    }
}
