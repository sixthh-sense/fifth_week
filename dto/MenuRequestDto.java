package com.sparta.fifthweek.dto;

import lombok.Getter;

@Getter
public class MenuRequestDto {
    private Long restaurantId;
    private String name;// 사용자 입력
    private Long price; // 사용자 입력
}
