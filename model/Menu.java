package com.sparta.fifthweek.model;

import com.sparta.fifthweek.dto.MenuRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Menu extends Timestamped{

    //"음식(메뉴)"의 ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    //"음식점"의 ID
    @Column(nullable=false)
    private Long restaurantId;

    //메뉴 이름
    @Column
    private String name;

    //메뉴 가격
    @Column
    private Long price;
    // 여기까지가 기본

    public Menu(MenuRequestDto requestDto, Long restaurantId) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.restaurantId = restaurantId;
    }
    // restaurantId가 restaurant table의 primary key를 말하는 거라고 알아들을 수 있을까? 이것만으론 불안
    // controller에서 정의해주는 걸로 ok일까?

    public Menu(String name, Long price, Long restaurantId) {
        this.name=name;
        this.price=price;
        this.restaurantId=restaurantId;
    }

}
