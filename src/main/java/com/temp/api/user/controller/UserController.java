package com.temp.api.user.controller;

import com.temp.api.common.Annotation.CurrentUser;
import com.temp.api.common.dto.CommonSuccessResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.common.security.CustomUser;
import com.temp.api.user.domain.OrdersEntity;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.dto.OrderListDto;
import com.temp.api.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 API
     * @param joinParam
     * @return ResponseEntity
     */
    @PostMapping("/join")
    public ResponseEntity<CommonSuccessResponse> join(@Valid @RequestBody JoinParam joinParam) {
        UserInfoEntity savedUser = userService.join(joinParam);

        CommonSuccessResponse response = CommonSuccessResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(savedUser)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 배달주문 API (단순 가데이터 입력용)
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/order")
    public ResponseEntity<CommonSuccessResponse> order(@RequestBody Map<String, String> request,
                                                       @CurrentUser UserInfoEntity currentUser) {
        OrdersEntity savedOrder = userService.orderInsert(currentUser.getUserCode(), request.get("toAddress"));

        CommonSuccessResponse response = CommonSuccessResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(savedOrder)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 배달조회 API
     * @param period
     * @return ResponseEntity
     */
    @GetMapping("/order-list")
    public ResponseEntity<CommonSuccessResponse> selectOrderList(@RequestParam @Min(1) @Max(3) Integer period,
                                                                    @CurrentUser UserInfoEntity currentUser) {
        // service layer 로 전달할 dto 생성
        OrderListDto orderListDto = OrderListDto.builder()
                .userCode(currentUser.getUserCode())
                .role(currentUser.getRole())
                .period(period)
                .build();

        List<OrdersEntity> orderList = userService.selectOrderList(orderListDto);

        CommonSuccessResponse response = CommonSuccessResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(orderList)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/order/{orderCode}")
    public ResponseEntity<CommonSuccessResponse> order(@RequestBody Map<String, String> request,
                                                       @PathVariable Long orderCode,
                                                       @CurrentUser UserInfoEntity currentUser) {
        OrdersEntity savedOrder = userService.orderUpdate(orderCode, currentUser.getUserCode(), request.get("toAddress"));

        CommonSuccessResponse response = CommonSuccessResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(savedOrder)
                .build();

        return ResponseEntity.ok(response);
    }
}
