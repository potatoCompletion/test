package com.temp.api.user.dto;

import com.temp.api.common.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class UserParam {
    @NotBlank(message = "id를 입력해주세요.")
    @Size(max = 20, message = "id는 20자 이내로 설정해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 12, message = "비밀번호는 12자 이상으로 설정해주세요.")
    @Pattern(regexp = "(?=.{12,})(" +
            "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])|" +
            "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])|" +
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])" +
            ").*",
            message = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 10, message = "이름을 확인해주세요. (10자 초과)")
    private String userName;
    private Roles role;

}
