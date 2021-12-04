package com.sparta.fifthweek.repository;

import com.sparta.fifthweek.model.Food;
import com.sparta.fifthweek.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    //List<Food> findAllByRestaurantId(Long restaurantId);
    //OrderByModifiedAtDesc

    //Optional<Food> findByNameAndRestaurantId(String name, Long RestaurantId);

    Optional<Food> findByNameAndRestaurant(String name, Restaurant restaurant);

    List<Food> findAllByRestaurant(Restaurant restaurant);
}
