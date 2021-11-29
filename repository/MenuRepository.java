package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurantIdOrderByModifiedAtDesc(Long restaurantId);

    Optional<Menu> findByNameAndRestaurantId(String name, Long RestaurantId);
}
