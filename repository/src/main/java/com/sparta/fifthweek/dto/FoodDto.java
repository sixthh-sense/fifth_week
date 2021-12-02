package com.sparta.fifthweek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FoodDto {

    private Long id; // 처음엔 사용자 입력했다고 설정하지 않았지만 이후에 그렇게 설정. // 예제 그림을 보니 PathVariable로 restaurantId를 구하는 것 같고 Request쪽에는 name, price밖에 없어서 원래 했던 대로 복귀
    // test code 보고 회복. 원래는 MenuRequestDto였으나 FoodDto로 class name 바꿈.
    // 그리고 단순히 FoodDto로 머무는 게 아니라 List를 만들어야 하는듯(request양식)
    private String name;// 사용자 입력

    private int price; // 사용자 입력 // test code로 Long에서 int로.
}
