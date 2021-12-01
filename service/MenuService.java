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

    private final MenuRepository MenuRepository;
    private final RegisterRepository RegisterRepository;

    @Transactional
    public void registerMenu(List<FoodDto> requestDtoList, Long restaurantId) {
        // MenuController의 PostMapping 입력 변수를 그대로 가지고 옴.
        //Restaurant restaurant = new Restaurant();

        Restaurant restaurant = RegisterRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));

        for (FoodDto fDto : requestDtoList) {
            Food food = validateFood(fDto, restaurant);
            // 여기서 fDto, restaurant 입력 변수를 2개 설정하는 이유는?
            // 반드시 그래야 한다, 라기보단 필요에 의해서 그리 된 듯한데
            // 1개만 넣었을 때랑 2개만 넣었을 때 정확히 어떤 식으로 data가 설정되는지 상상하기 어렵다.
            // Food 엔 id, restaurant(restaurant_id), name, price 가 존재
            // fDto엔 id(food의 id), name, price 존재
            // restaurant는 연결용 column. restaurant_id 개념.

            // 리스트가 개체로 분해되고 for문을 사용함으로써 list안에 있는 전체 요소들에게 조건을 죄다 적용
        }
    }

    private Food validateFood(FoodDto fDto, Restaurant restaurant) {
        // DB 연계하는 과정에서 생성된, Food Entity에 존재하는 restaurant_id를 나타내는 Restaurant restaurant
        // 근데 이러면 Restaurant entity table과 restaurant_id와 헷갈리지 않나?
        // 설마 return 형태를 Food Entity로 설정해서 헷갈리지 않는 건 아니겠지?

        final int MIN_MENU = 100;
        final int MAX_MENU = 1000000;

        String name = fDto.getName();
        int price = fDto.getPrice();
        //Long restaurantId = restaurant.getId();
        //restaurantId = restaurant.getId();
        //이건 꼭 필요한 코드일까? restaurant의 primary key라는 걸 나타내기 위해?

        // 여기서 상수 설정을 하려면 어떻게 해야 하지? MIN&MAX_MENU 하고 싶은데 방법을 모르겠네.

        // 메뉴명(같은 음식점 내 중복 불가)
        Optional<Food> found = MenuRepository.findByNameAndRestaurant(name, restaurant);
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
//        Food food = new Food(requestDto, restaurantId);
//        MenuRepository.save(food); return food;
        // fDto & restaurant 해서 Food에 소속된 전체 요소들(4항목) 짝이 맞긴 하다. 짝이 안 맞으면 repository에 정상적으로 적용이 안 되나?
        // Food entity의 id는 자동생성이 되니 "굳이 짝을 맞춰줄 필요는 없는" 류에 속할까? 이건 restaurant_Id에도 적용이 되나?
        // 이럴 경우에 표시되는 오류는 뭘까? 직접 경험하고 싶진 않지만 행여나 겪게 되는 일이 있다면 바로 알아보고 싶다.

        return MenuRepository.save(food);
    }

    public List<FoodDto> getMenu(Long restaurantId) {
        // static 정의 다시 한번 알아봐야.

        Restaurant restaurant = RegisterRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("해당 음식점은 존재하지 않습니다."));
        // Restaurant 클래스에서 데려온 "일반 일꾼"으로 보이지만 미리 Food에 id쪽으로 등록이 됐으니 id개념만 연관이 되면 자동적으로 id쪽으로 해석이 되나?

        List<Food> foods = MenuRepository.findAllByRestaurant(restaurant);
        List<FoodDto> response = new ArrayList<>();

        for (Food food : foods) {
            response.add(foodDtoSetting(food));
        }

        return response;
    }

    private FoodDto foodDtoSetting(Food food) {
        // return값이 FoodDto라는 걸 허투루 보지 말자.
        // foodDtoSetting(food) 형식을 사용하지 않고 저 response.add() 안에 넣어주고 싶으면
        // new foodDto로 food.getId() 3요소를 한번 싸주는 게 필요.
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