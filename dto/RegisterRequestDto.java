package com.sparta.fifthweek.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDto {
    private String name; // 사용자 입력
    private Long minOrderPrice; // 사용자 입력
    private Long deliveryFee; // 사용자 입력
}
