package com.temp.api.user.controller;

import com.temp.api.common.dto.CommonSuccessResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 API
     * @param joinParam
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<CommonSuccessResponse> join(@Valid @RequestBody JoinParam joinParam) {
        var savedUserId = userService.join(joinParam);

        CommonSuccessResponse response = CommonSuccessResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(savedUserId)
                .build();

        return ResponseEntity.ok(response);
    }


}
