package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFoodRepository extends JpaRepository<OrderFood, Long> {
    List<OrderFood> findAll();

    //List<OrderFood> findAllByResponseOrder(OrderRestaurant orderRestaurant);
}
