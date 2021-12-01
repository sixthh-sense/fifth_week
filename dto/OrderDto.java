package com.sparta.fifthweek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDto {
    // 필요하다면 이 부분에 "내부 클래스"를 하나 더 만들 수 있긴 하지만 결합도가 높아져서(분자구조, 2당구조에서 3당구조가 되는 것과 비슷?) 그리 일반적으로 선호하는 방식은 아니다?

    private String restaurantName;

    private List<FoodOrderDto> foods;
    // Response Body Sample에는 foods: [ "name" : value1, "quantity" : value2, "price" : value3 ]
    // name은 food, 그러니까 메뉴의 이름.
    // 여기서의 price는 단일 가격이 아니라 "해당 메뉴에 소모한 모든 비용" 즉, quantity*price의 sum.

    private int deliveryFee;
    private int totalPrice;

//    public OrderDto() {
//
//    }
    // "총합" 개념의 price를 모두 더한 값 + deliveryFee
}
