package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.FoodDto;
import com.sparta.fifthweek.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // @ResponseBody
public class FoodController {

    private final MenuService MenuService;

    // 메뉴 입력(음식점 id별로)
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerMenu(@PathVariable Long restaurantId, @RequestBody List<FoodDto> requestDtoList) {
        MenuService.registerMenu(requestDtoList, restaurantId);
    }
    // 문제 잘못 읽음. List<Food>를 출력해야 함.
    // List<FoodDto>

    // 메뉴 출력(음식점 id별로)
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<FoodDto> getMenu(@PathVariable Long restaurantId) {
        return MenuService.getMenu(restaurantId);
        // 이 상태로 하면 restaurantId가 찍혀나오기 때문에 얘를 빼줘야 함

    }
}

//@PostMapping("/restaurant/{restaurantId}/food/register")
//    public Food registerMenu(@PathVariable Long restaurantId, @RequestBody FoodDto requestDto) {
//        return MenuService.registerMenu(requestDto, restaurantId);
//    }
//    // 문제 잘못 읽음. List<Food>를 출력해야 함.

//public Menu registerMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDto requestDto) {
//        return MenuService.registerMenu(requestDto, restaurantId);
//    }
//@PostMapping("/restaurant/{restaurantId}/food/register")
//    public Menu registerMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDto requestDto) {
//        return MenuService.registerMenu(requestDto);
//    }