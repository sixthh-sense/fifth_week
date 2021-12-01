package com.sparta.fifthweek.model;

import com.sparta.fifthweek.dto.RestaurantDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Restaurant {

    // primary key: Id, "전략"은 "자동으로 생성 및 증가": 조합이 아닌 기초 Entity 개념? 그래서 primary key generation type을 auto로 설정?
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private int minOrderPrice;

    // Q. 배달료가 0인 건 nullable한 범위에 들어가나?
    // 일단 "반드시 채워넣어야 할 항목"이란 관점에서 nullable=false라고 기록.
    @Column(nullable = false)
    private int deliveryFee;

    // 일단 문제1번 대상으로 설정만 해두고 문제 2, 3번 항목 & Controller등에서 요구하는 양식은 나중에.

    public Restaurant(RestaurantDto requestDto) {
        this.name = requestDto.getName();
        this.minOrderPrice = requestDto.getMinOrderPrice();
        this.deliveryFee = requestDto.getDeliveryFee();
    } // @PostMapping("/restaurant/register") 겨냥.
    // id는 자동이라서 굳이 기입 안 해도 ok(?) -> 오류 나면 기입해야겠다.

    // Cascade는 collapse개념으로 이해하면 될 듯? "같이 휩쓸려서 무너질" data
    // mapped"By"니까 얘가 가리키는 에가 주체. food가 One이란 건가?
//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    List<Food> food = new ArrayList<>();
    // FoodDto 에 관한 List도 써줘야 하나? Entity가 아니라서 필요없나?

    @Builder // 안전한 객체생성 도우미. DB 강의 더 듣고 더 자세히 알아보자.
    public Restaurant(String name, int minOrderPrice, int deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

}
