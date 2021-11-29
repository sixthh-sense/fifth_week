package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.RegisterRequestDto;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.RegisterRepository;
import com.sparta.fifthweek.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RegisterController {

    private final RegisterRepository RegisterRepository;
    private final RestaurantService RestaurantService;

    // 음식점 등록
   @PostMapping("/restaurant/register")
    public Restaurant registerRestaurants(@RequestBody RegisterRequestDto requestDto) {
       // #1 (name, minOrderPrice, deliveryFee) 모두 사용자가 입력한 값에서 받아올 수 있는 정보니 requestDto에서 받아올 수 있음.
       // GET에 표현되는 항목은 4개인데 나머지 1개의 항목은 id(primary key)이므로 그쪽은 알아서 정해지는 값.
        return RestaurantService.registerRestaurants(requestDto);
    }

    // 음식점 조회
    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {
       return RegisterRepository.findAllByOrderByModifiedAtDesc();
    }
}
