package com.sparta.fifthweek.dto;

import lombok.Getter;

@Getter
public class RestaurantDto {
    // test code 보고 Dto 이름 바꿈. 원래는 RegisterRequestDto
    private Long id; // test code 보고 추가...했다가 다시 지움...했다가 다시 추가(맨 마지막 부분)
    private String name; // 사용자 입력
    private int minOrderPrice; // 사용자 입력 + test code 보고 int 추가
    private int deliveryFee; // 사용자 입력 + test code 보고 int 추가

    public RestaurantDto(RestaurantDto requestDto) {
        this.id = requestDto.getId();
        this.name = requestDto.getName();
        this.minOrderPrice = requestDto.getMinOrderPrice();
        this.deliveryFee = requestDto.getDeliveryFee();
    }

    public RestaurantDto(Long id, String name, int minOrderPrice, int deliveryFee) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }
}
