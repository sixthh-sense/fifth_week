package com.sparta.fifthweek.dto;

import java.util.List;

public class OrderRequestDto {
    private Long restaurantId;
    private List<FoodOrderRequestDto> foods;
    // RequestBody sample에 foods [ id: something, quantity: something ]
}
