package com.sparta.fifthweek.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {
    private Long restaurantId;
    private List<FoodOrderRequestDto> foods;
    // RequestBody sample에 foods [ id: something, quantity: something ]
}
