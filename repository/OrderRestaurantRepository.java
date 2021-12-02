package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.OrderRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRestaurantRepository extends JpaRepository<OrderRestaurant, Long> {
    List<OrderRestaurant> findAll();

    //List<OrderRestaurant> findAllByRestaurant(Restaurant restaurant);
}
