package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.FoodDto;
import com.sparta.fifthweek.model.Food;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.MenuRepository;
import com.sparta.fifthweek.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MenuService {
    ;
    private final MenuRepository MenuRepository;
    private final RegisterRepository RegisterRepository;

    @Transactional
    public void registerMenu(List<FoodDto> requestDtoList, Long restaurantId) {
        //Restaurant restaurant = new Restaurant();

        Restaurant restaurant = RegisterRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));

        for (FoodDto fDto : requestDtoList) {
            Food food = validateFood(fDto, restaurant);
        }
    }

    private Food validateFood(FoodDto fDto, Restaurant restaurant) {

        String name = fDto.getName();
        int price = fDto.getPrice();
        //Long restaurantId = restaurant.getId();
        //restaurantId = restaurant.getId();
        //이건 꼭 필요한 코드일까? restaurant의 primary key라는 걸 나타내기 위해?

        // 여기서 상수 설정을 하려면 어떻게 해야 하지? MIN&MAX_MENU 하고 싶은데 방법을 모르겠네.

        // 메뉴명(같은 음식점 내 중복 불가)
        Optional<Food> found = MenuRepository.findByNameAndRestaurant(name, restaurant);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 메뉴 명칭입니다.");
        } // "같은" 가게에서 검색하려면 어떻게 해야 하는지 모르겠음 -> find할 때 기준을 2개 넣는 것으로 해결

        // 가격(100원과 백만원 사이, 100원 단위로만 입력 가능) - 아닐 경우 에러 발생
        if (price < 100 || price > 1000000) {
            throw new IllegalArgumentException("가격은 100원 이상, 1,000,000원 이하의 범위로 입력해주십시오.");
        }
        if (price % 100 != 0) {
            throw new IllegalArgumentException("가격은 100원 단위로 입력해주십시오.");
        }

        Food food = new Food(fDto, restaurant);
//        Food food = new Food(requestDto, restaurantId);
//        MenuRepository.save(food); return food;
        return MenuRepository.save(food);
    }

    public List<FoodDto> getMenu(Long restaurantId) { // static 정의 다시 한번 알아봐야.

        Restaurant restaurant = RegisterRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));

        List<Food> foods = MenuRepository.findAllByRestaurant(restaurant);
        List<FoodDto> response = new ArrayList<>();

        for (Food food : foods) {
            response.add(foodDtoSetting(food));
        }

        return response;
    }

    private FoodDto foodDtoSetting(Food food) {
        return new FoodDto(
                // 중간에 빨간줄 떠서 몰랐는데 MenuResponseDto쪽에 생성자 안 만들었음
                food.getId(),
                food.getName(),
                food.getPrice()
        );
    }

}

//public List<Menu> getMenu(Long restaurantId) { // static 정의 다시 한번 알아봐야.
//        return MenuRepository.findAllByRestaurantIdOrderByModifiedAtDesc(restaurantId);
//    }

//public Menu registerMenu(MenuRequestDto requestDto) {
//        Long restaurantId = requestDto.getRestaurantId();
//        String name = requestDto.getName();
//        Long price = requestDto.getPrice();


//Long id = responseDto.getId();
//        String name = responseDto.getName();
//        Long price = responseDto.getPrice();
//
//        List<Menu> menu = MenuRepository.findAllByRestaurantIdOrderByModifiedAtDesc(restaurantId);