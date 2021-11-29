package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.MenuRequestDto;
import com.sparta.fifthweek.model.Menu;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository MenuRepository;

    public List<Menu> getMenu(Long restaurantId) { // static 정의 다시 한번 알아봐야.
        return MenuRepository.findAllByRestaurantIdOrderByModifiedAtDesc(restaurantId);
    }

    @Transactional
    public Menu registerMenu(MenuRequestDto requestDto, Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurantId = restaurant.getId();
        // 이건 꼭 필요한 코드일까? restaurant의 primary key라는 걸 나타내기 위해?

        String name = requestDto.getName();
        Long price = requestDto.getPrice();

        // 메뉴명(같은 음식점 내 중복 불가)
        Optional<Menu> found = MenuRepository.findByNameAndRestaurantId(name, restaurantId);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 메뉴 명칭입니다.");
        } // "같은" 가게에서 검색하려면 어떻게 해야 하는지 모르겠음


        // 가격(100원과 백만원 사이, 100원 단위로만 입력 가능) - 아닐 경우 에러 발생
        if (price < 100 || price > 1000000) {
            throw new IllegalArgumentException("가격은 100원 이상, 1,000,000원 이하의 범위로 입력해주십시오.");
        }
        if (price % 100 != 0) {
            throw new IllegalArgumentException("가격은 100원 단위로 입력해주십시오.");
        }

        Menu menu = new Menu(requestDto, restaurantId);
        MenuRepository.save(menu);
        return menu;
    }
}
