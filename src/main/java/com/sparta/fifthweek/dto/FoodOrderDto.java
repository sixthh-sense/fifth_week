package com.sparta.fifthweek.dto;

import lombok.Getter;

@Getter
public class FoodOrderDto {

    String name;
    int quantity;
    int price;

    public FoodOrderDto(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    // @NoArgs, @AllArgs등 덧붙였다가 내 이해력 향상을 위해 일부러 @Getter만 도입. 막상 FoodDto에는 죄다 넣긴 했지만;

    // int price가 "메뉴" 총합 개념이란 걸 어떻게 표현하지?
    // Food Entity에서 FoodOrderDto와 연관지어서 생성자를 만들어야?
}
