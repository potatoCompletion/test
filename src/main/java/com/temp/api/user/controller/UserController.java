package com.temp.api.user.controller;

import com.temp.api.common.Annotation.CurrentUser;
import com.temp.api.common.dto.CommonResponse;
import com.temp.api.common.enums.ResponseMessage;
import com.temp.api.user.domain.OrdersEntity;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.dto.OrderListDto;
import com.temp.api.user.dto.OrderParam;
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

    @PostMapping("")
    public ResponseEntity<CommonResponse> join(@Valid @RequestBody JoinParam joinParam) {
        userService.join(joinParam);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 회원가입 API
     * @param joinParam
     * @return ResponseEntity
     */
//    @PostMapping("/join")
//    public ResponseEntity<CommonResponse> join(@Valid @RequestBody JoinParam joinParam) {
//        userService.join(joinParam);
//
//        CommonResponse response = CommonResponse.builder()
//                .status(HttpStatus.OK)
//                .message(ResponseMessage.SUCCESS)
//                .build();
//
//        return ResponseEntity.ok(response);
//    }

    /**
     * 배달주문 API (단순 가데이터 입력용, insert)
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/orders")
    public ResponseEntity<CommonResponse> insertOrder(@RequestBody Map<String, String> request,
                                                      @CurrentUser UserInfoEntity currentUser) {
        OrdersEntity savedOrder = userService.insertOrder(currentUser.getUserCode(), request.get("toAddress"));

        CommonResponse response = CommonResponse.builder()
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
    @GetMapping("/orders")
    public ResponseEntity<CommonResponse> selectOrderList(@RequestParam(value = "period") @Min(0) @Max(3) Integer period,
                                                          @CurrentUser UserInfoEntity currentUser) {

        // service layer 로 전달할 dto 생성
        OrderListDto orderListDto = OrderListDto.builder()
                .userCode(currentUser.getUserCode())
                .role(currentUser.getRole())
                .period(period)
                .build();

        List<OrdersEntity> orderList = userService.selectOrderList(orderListDto);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(orderList)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 주문내역 수정 API (update)
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/orders/{orderCode}")
    public ResponseEntity<CommonResponse> updateOrder(@RequestBody OrderParam request,
                                                      @PathVariable Long orderCode,
                                                      @CurrentUser UserInfoEntity currentUser) {
        OrdersEntity savedOrder = userService.updateOrder(orderCode, currentUser.getUserCode(), request);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message(ResponseMessage.SUCCESS)
                .data(savedOrder)
                .build();

        return ResponseEntity.ok(response);
    }
}
