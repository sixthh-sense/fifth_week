package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.RestaurantDto;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    //private final RestaurantRepository RestaurantRepository;
    private final RestaurantService RestaurantService;

    // 음식점 등록
   @PostMapping("/restaurant/register")
    public Restaurant registerRestaurants(@RequestBody RestaurantDto requestDto) {
       // #1 (name, minOrderPrice, deliveryFee) 모두 사용자가 입력한 값에서 받아올 수 있는 정보니 requestDto에서 받아올 수 있음.
       // GET에 표현되는 항목은 4개인데 나머지 1개의 항목은 id(primary key)이므로 그쪽은 알아서 정해지는 값.
       // RestaurantDto를 상정한 걸까? return
        return RestaurantService.registerRestaurants(requestDto);
    }

    // 음식점 조회
    @GetMapping("/restaurants")
    public List<RestaurantDto> getRestaurants() {
       // Timestamped 넣었다가 test에 적합하지 않을 듯해서 뺌.
       return RestaurantService.letOutRestaurants();
    }
}
