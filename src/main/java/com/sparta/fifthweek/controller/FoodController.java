package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.FoodDto;
import com.sparta.fifthweek.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // @ResponseBody
public class FoodController {

    private final FoodService FoodService;

    // 메뉴 입력(음식점 id별로)
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerMenu(@PathVariable Long restaurantId, @RequestBody List<FoodDto> requestDtoList) {
        FoodService.registerMenu(requestDtoList, restaurantId);
    }
    // List<FoodDto>

    // 메뉴 출력(음식점 id별로)
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<FoodDto> getMenu(@PathVariable Long restaurantId) {
        return FoodService.getMenu(restaurantId);
        // 이 상태로 하면 restaurantId가 찍혀나오기 때문에 얘를 빼줘야 함
    }
}