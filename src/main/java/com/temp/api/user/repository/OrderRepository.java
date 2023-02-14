package com.temp.api.user.repository;

import com.temp.api.user.domain.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    OrdersEntity save(OrdersEntity order);
    Optional<List<OrdersEntity>> findAllByRequestUserCodeAndCreatedDateBetween(Long requestUserCode,
                                                                                LocalDateTime startDate,
                                                                                LocalDateTime endDate);
    Optional<List<OrdersEntity>> findAllByRiderUserCodeAndCreatedDateBetween(Long riderUserCode,
                                                                                LocalDateTime startDate,
                                                                                LocalDateTime endDate);

    Optional<OrdersEntity> findByOrderCode(Long orderCode);
}
