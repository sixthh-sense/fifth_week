package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.RegisterRequestDto;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RegisterRepository RegisterRepository;

    @Transactional
    public Restaurant registerRestaurants(RegisterRequestDto requestDto) throws IllegalArgumentException {

        Long minOrderPrice = requestDto.getMinOrderPrice();
        Long deliveryFee = requestDto.getDeliveryFee();

        // 여기에 error 날 조건 정해주기? error는 Illegal 어쩌고 그거면 될까?
        if (minOrderPrice > 100000 || minOrderPrice < 1000) {
            throw new IllegalArgumentException("최소주문가격은 1,000원 이상 100,000원 이하의 범위로 입력해주십시오.");
                    //"최소주문가격은 1,000원 이상 100,000 이하의 범위에서 입력해주십시오.";
        }
        if (minOrderPrice % 100 != 0) {
            throw new IllegalArgumentException("최소주문가격은 100원 단위로 입력해주십시오.");
        }

        if (deliveryFee > 10000 || deliveryFee < 0 ) {
            throw new IllegalArgumentException("기본 배달비는 0원 이상 10,000원 이하의 범위로 입력해주십시오.");
        }
        if (deliveryFee % 500 != 0) {
            throw new IllegalArgumentException("기본 배달비는 500원 단위로 입력해주십시오.");
        }

        Restaurant restaurant = new Restaurant(requestDto);
        RegisterRepository.save(restaurant);

        return restaurant;
    }
}
