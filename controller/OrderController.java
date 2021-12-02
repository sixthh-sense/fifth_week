package com.sparta.fifthweek.controller;

import com.sparta.fifthweek.dto.OrderDto;
import com.sparta.fifthweek.dto.OrderRequestDto;
import com.sparta.fifthweek.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {
    // Long restaurantId
    // 음식 목록 foods { id: value, quantity: value } // 여긴 어떻게? // RequestBody (restaurantId, List_Dto(Id, quantity))

    private final OrderService OrderService;
    //private final OrderRepository OrderRepository;

    // POST로 가져오는 건 OrderRequestDto
    @PostMapping("/order/request")
    public OrderDto registerOrder(@RequestBody OrderRequestDto requestDto) {
        return OrderService.registerOrder(requestDto);
    }


    // GET으로 보여주고 싶은 형태는 OrderDto의 List.
    // 검색은 음식점별로.
    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        // 이렇게 Restaurant restaurant 라고 대충 적어놔도 id 개념이라고 알아듣나?
        // /orders url에서 신호를 보내면 getOrders() method가 실행되고 List<OrderDto>가 돌아온다.
        // 특정 값이 null일 때 그 부분만 빼고 보여달란 말을 어떻게 할까?
        return OrderService.getOrders();
    }

}
