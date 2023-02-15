package com.temp.api.user.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.temp.api.common.enums.Roles;
import com.temp.api.user.domain.OrdersEntity;
import com.temp.api.user.domain.UserInfoEntity;
import com.temp.api.user.dto.JoinParam;
import com.temp.api.user.dto.OrderListDto;
import com.temp.api.user.dto.OrderParam;
import com.temp.api.user.repository.OrderRepository;
import com.temp.api.user.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final OrderRepository orderRepository;
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";

    /**
     * 유저 회원가입(저장)
     *
     * @param joinParam
     * @return UserInfoEntity
     */
    public UserInfoEntity join(JoinParam joinParam) {

        // 아이디 중복 검증
        if (userInfoRepository.existsByUserId(joinParam.getUserId())) {
            throw new DuplicateRequestException("이미 존재하는 아이디 입니다.");
        }

        UserInfoEntity newUser = UserInfoEntity.builder()
                .userId(joinParam.getUserId())
                .password(passwordEncoder.encode(joinParam.getPassword()))  // 비밀번호 암호화
                .name(joinParam.getUserName())
                .role(joinParam.getRole())
                .build();

        return userInfoRepository.save(newUser);
    }

    /**
     * 최근 로그인 업데이트
     *
     * @param userId
     */
    public void updateLastLogin(String userId) {
        UserInfoEntity user = userInfoRepository.findByUserId(userId)
                .orElseThrow();

        user.changeLastLogin(LocalDateTime.now());
    }

    /**
     * 주문 데이터 생성 (insert)
     *
     * @param userCode
     * @param toAddress
     * @return OrderEntity
     */
    public OrdersEntity insertOrder(Long userCode, String toAddress) {

        OrdersEntity order = OrdersEntity.builder()
                .requestUserCode(userCode)
                .toAddress(toAddress)
                .build();

        return orderRepository.save(order);
    }

    /**
     * 주문 데이터 수정 (update)
     *
     * @param orderCode
     * @param userCode
     * @param orderParam
     * @return OrderEntity
     */
    public OrdersEntity updateOrder(Long orderCode, Long userCode, OrderParam orderParam) {

        OrdersEntity order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow();

        // 해당 주문이 요청유저의 것이 맞는지 검증 && 업데이트 가능여부 체크
        if (!order.getRequestUserCode().equals(userCode) && order.isUpdatable()) {
            throw new InvalidParameterException();  // TODO: 예외 처리
        }

        order.changeToAddress(orderParam.getToAddress());

        return order;
    }

    /**
     * 요청 유저의 배달내역 (라이더, 일반유저(업주) 공용)
     *
     * @param orderListDto
     * @return Optional<List<OrderEntity>>
     */
    public List<OrdersEntity> selectOrderList(OrderListDto orderListDto) {
        // 입력받은 날짜 기반으로 조회일자 설정
        HashMap<String, LocalDateTime> dateTimeHashMap = autoSetDateTime(orderListDto.getPeriod());

        if (orderListDto.getRole().equals(Roles.RIDER)) {   // user.role 이 rider 일 경우
            return orderRepository.findAllByRiderUserCodeAndCreatedDateBetweenOrderByCreatedDate(orderListDto.getUserCode(),
                    dateTimeHashMap.get(START_DATE),
                    dateTimeHashMap.get(END_DATE))
                    .orElseThrow(() -> {
                        throw new NoSuchElementException();
                    });
        }

        return orderRepository.findAllByRequestUserCodeAndCreatedDateBetweenOrderByCreatedDate(orderListDto.getUserCode(),
                dateTimeHashMap.get(START_DATE),
                dateTimeHashMap.get(END_DATE))
                .orElseThrow(() -> {
                    throw new NoSuchElementException();
                });
    }

    /**
     * 오늘 날짜로 부터 {입력받은 날짜} 전까지의 기간을 설정해주는 메서드
     * @param period
     * @return HashMap<String, LocalDateTime>
     */
    private HashMap<String, LocalDateTime> autoSetDateTime(int period) {
        HashMap<String, LocalDateTime> dateTimeHashMap = new HashMap<>();

        dateTimeHashMap.put(START_DATE,
                LocalDateTime.of(LocalDate.now().minusDays(period), LocalTime.of(0, 0, 0)));
        dateTimeHashMap.put(END_DATE,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));

        return dateTimeHashMap;
    }

}
