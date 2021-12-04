package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.FoodDto;
import com.sparta.fifthweek.model.Food;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.FoodRepository;
import com.sparta.fifthweek.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository FoodRepository;
    private final RestaurantRepository RestaurantRepository;

    @Transactional
    public void registerMenu(List<FoodDto> requestDtoList, Long restaurantId) {

        Restaurant restaurant = RestaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));

        //List<Food> foods = new ArrayList<>();

        for (FoodDto fDto : requestDtoList) {
            Food food = validateFood(fDto, restaurant);
                //foods.add(food);
                //FoodRepository.saveAll(foods);
            // 이렇게 하면 123 식으로 표기되어야 할 것이 312로 나오던데 3이 12보다 먼저 저장되는 이유를 파악하지 못했음.
            // saveAll에 관한 이해 부족일까?
        }

    }

    private Food validateFood(FoodDto fDto, Restaurant restaurant) {
        // DB 연계하는 과정에서 생성된, Food Entity의 JoinColumn을 나타내는, Restaurant restaurant
        // 일단 항목 name은 restaurant_id로 설정. Restaurant의 전체 항목을 가져올 수 있는 창구 역할. id가 default인 이유는 걔가 pk라서 창구 역할로서 제일 적합.

        final int MIN_MENU = 100;
        final int MAX_MENU = 1000000;

        String name = fDto.getName();
        int price = fDto.getPrice();
        //Long restaurantId = restaurant.getId();
        //restaurantId = restaurant.getId();
        //이건 꼭 필요한 코드일까? restaurant의 primary key라는 걸 나타내기 위해?

        // 여기서 상수 설정을 하려면 어떻게 해야 하지? MIN&MAX_MENU 하고 싶은데 방법을 모르겠네.

        // 메뉴명(같은 음식점 내 중복 불가)
        Optional<Food> found = FoodRepository.findByNameAndRestaurant(name, restaurant);
        // 이런 데서는 Restaurant가 id개념이라고 추론하는 게 합리적이긴 한데, 그건 인간 기준이고. 컴퓨터 기준으로도 그렇나?
        // 애매한 걸 견디지 못하는 "바보"가 컴퓨터 아니었나? Food에서 찾는다고 왼쪽에서 말했고, Entity에 Restaurant restaurant가 등록이 돼서 문제X?

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 메뉴 명칭입니다.");
        } // "같은" 가게에서 검색하려면 어떻게 해야 하는지 모르겠음 -> find할 때 기준을 2개 넣는 것으로 해결
        // Optional이랑 isPresent랑 같이 안 쓰는 게 좋다고 어디선가 본 것 같은데

        // 가격(100원과 백만원 사이, 100원 단위로만 입력 가능) - 아닐 경우 에러 발생
        if (price < MIN_MENU || price > MAX_MENU) {
            throw new IllegalArgumentException("가격은 100원 이상, 1,000,000원 이하의 범위로 입력해주십시오.");
        }
        if (price % 100 != 0) {
            throw new IllegalArgumentException("가격은 100원 단위로 입력해주십시오.");
        }

        Food food = new Food(fDto, restaurant);
        // fDto & restaurant 해서 Food에 소속된 전체 요소들(4항목) 짝이 맞긴 하다. 짝이 안 맞으면 repository에 정상적으로 적용이 안 되나?
        // Food entity의 id는 자동생성이 되니 "굳이 짝을 맞춰줄 필요는 없는" 류에 속할까? 이건 restaurant_Id에도 적용이 되나?

        FoodRepository.save(food);

        return food;
    }

    public List<FoodDto> getMenu(Long restaurantId) {
        // static 정의 다시 한번 알아봐야.

        Restaurant restaurant = RestaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));
        // Restaurant 클래스에서 데려온 "일반 일꾼"으로 보이지만 미리 Food에 id쪽으로 등록이 됐으니 id개념만 연관이 되면 자동적으로 id쪽으로 해석이 되나?

        List<Food> foods = FoodRepository.findAllByRestaurant(restaurant);
        List<FoodDto> response = new ArrayList<>();

        for (Food food : foods) {
            response.add(foodDtoSetting(food));
        }
        return response;
    }

    private FoodDto foodDtoSetting(Food food) {
        return new FoodDto(
                // 중간에 빨간줄 떠서 몰랐는데 FoodDto쪽에 생성자 안 만들었음
                food.getId(),
                food.getName(),
                food.getPrice()
        );
    }
}