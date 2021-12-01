package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.ResponseOrder;
import com.sparta.fifthweek.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseOrderRepository extends JpaRepository<ResponseOrder, Long> {
    List<ResponseOrder> findAll();

    List<ResponseOrder> findAllByRestaurant(Restaurant restaurant);
}
