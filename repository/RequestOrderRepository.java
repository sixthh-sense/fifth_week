package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.RequestOrder;
import com.sparta.fifthweek.model.ResponseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestOrderRepository extends JpaRepository<RequestOrder, Long> {
    List<RequestOrder> findAll();

    List<RequestOrder> findAllByResponseOrder(ResponseOrder responseOrder);
}
