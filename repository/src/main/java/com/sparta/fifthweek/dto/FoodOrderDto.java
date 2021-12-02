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

//    public FoodOrderDto(String name, int quantity, int i) {
//    }
    // int price가 "메뉴" 총합 개념이란 걸 어떻게 표현하지?
    // Food Entity에서 FoodOrderDto와 연관지어서 생성자를 만들어야?
}
