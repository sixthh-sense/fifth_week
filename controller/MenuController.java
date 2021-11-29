package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.MenuRequestDto;
import com.sparta.fifthweek.model.Menu;
import com.sparta.fifthweek.repository.MenuRepository;
import com.sparta.fifthweek.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuRepository MenuRepository;
    private final MenuService MenuService;

    // 메뉴 입력(음식점 id별로)
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public Menu registerMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDto requestDto) {
        return MenuService.registerMenu(requestDto, restaurantId);
    }

    // 메뉴 출력(음식점 id별로)
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Menu> getMenu(@PathVariable Long restaurantId) {
        return MenuService.getMenu(restaurantId);

    }
}
