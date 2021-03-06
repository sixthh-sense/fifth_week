package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAll();
    //List<Restaurant> findAllByOrderByModifiedAtDesc(); // Timestamped의 흔적.
}
