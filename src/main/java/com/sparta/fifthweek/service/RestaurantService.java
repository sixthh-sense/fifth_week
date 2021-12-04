package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.RestaurantDto;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository RestaurantRepository;

    @Transactional
    public Restaurant registerRestaurants(RestaurantDto requestDto) throws IllegalArgumentException {

        int minOrderPrice = requestDto.getMinOrderPrice();
        int deliveryFee = requestDto.getDeliveryFee();

        // 배달료 설정
        final int MAX_DELIVERY = 10000;
        final int MIN_DELIVERY = 0;
        //주문가격 설정
        final int MAX_PRICE = 100000;
        final int MIN_PRICE = 1000; // 원래는 private static 도 붙여 썼는데 여기는 왜 안 될까? @Component가 아니라서? 아니면 @Transactional이라서?

        // 여기에 error 날 조건 정해주기? error는 Illegal 어쩌고 그거면 될까?
        // 직접적으로 숫자를 써주기보다는 MIN_PRICE, MAX_PRICE 등으로 변수를 가져와 설정해주면
        // 이후에 수정하기가 더 편할 듯. 센스쟁이의 덕목?
        if (minOrderPrice > MAX_PRICE || minOrderPrice < MIN_PRICE) {
            throw new IllegalArgumentException("최소주문가격은 1,000원 이상 100,000원 이하의 범위로 입력해주십시오.");
                    //"최소주문가격은 1,000원 이상 100,000 이하의 범위에서 입력해주십시오.";
        }
        if (minOrderPrice % 100 != 0) {
            throw new IllegalArgumentException("최소주문가격은 100원 단위로 입력해주십시오.");
        }

        if (deliveryFee > MAX_DELIVERY || deliveryFee < MIN_DELIVERY ) {
            throw new IllegalArgumentException("기본 배달비는 0원 이상 10,000원 이하의 범위로 입력해주십시오.");
        }
        if (deliveryFee % 500 != 0) {
            throw new IllegalArgumentException("기본 배달비는 500원 단위로 입력해주십시오.");
        }

        Restaurant restaurant = new Restaurant(requestDto);
        RestaurantRepository.save(restaurant);
        // 여기 save 괄호 안에 들어간 건 save되기 전의 restaurant

        return restaurant;
        // 얘는 save된 후의 restaurant. 강의를 들어도 이해는 잘 안 되지만 둘이 다르게 인식된다고 한다.
    }

    public List<RestaurantDto> letOutRestaurants() {
        return beDtos(RestaurantRepository.findAll());
    }

    private List<RestaurantDto> beDtos(List<Restaurant> restaurantList) {
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {

            Long id = restaurant.getId();
            String name = restaurant.getName();
            int minOrderPrice = restaurant.getMinOrderPrice();
            int deliveryFee = restaurant.getDeliveryFee();

            restaurantDtoList.add(new RestaurantDto(id, name, minOrderPrice, deliveryFee));
        }

        return restaurantDtoList;
    }
}
