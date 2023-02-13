package com.temp.api.user.repository;

import com.temp.api.user.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity save(OrderEntity order);
    Optional<List<OrderEntity>> findAllByRequestUserCode(Long requestUserCode);
    Optional<List<OrderEntity>> findAllByRiderUserCode(Long riderUserCode);
}
