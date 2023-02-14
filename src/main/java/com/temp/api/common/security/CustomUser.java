package com.temp.api.common.security;

import com.temp.api.user.domain.UserInfoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class CustomUser extends User {
    private UserInfoEntity userInfoEntity;

    public CustomUser(UserInfoEntity user, List<GrantedAuthority> authorities) {
        super(user.getUserId(), user.getPassword(), authorities);
        this.userInfoEntity = user;
    }
}
