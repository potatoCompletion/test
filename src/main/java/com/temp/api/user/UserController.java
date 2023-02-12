package com.temp.api.user;

import com.temp.api.user.dto.UserParam;
import com.temp.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public String join(@Valid @RequestBody UserParam userParam) {
        Long id = userService.join(userParam);

        return "";
    }

    @PostMapping("/login")
    public String logIn(UserParam userParam) {



        return "";
    }
}
