package com.sparta.fifthweek.model;

import com.sparta.fifthweek.dto.RegisterRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Restaurant extends Timestamped {

    // primary key: Id, "전략"은 "자동으로 생성 및 증가"
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private Long minOrderPrice;

    // Q. 배달료가 0인 건 nullable한 범위에 들어가나?
    // 일단 "반드시 채워넣어야 할 항목"이란 관점에서 nullable=false라고 기록.
    @Column(nullable = false)
    private Long deliveryFee;

    // 일단 문제1번 대상으로 설정만 해두고 문제 2, 3번 항목 & Controller등에서 요구하는 양식은 나중에.

    public Restaurant(RegisterRequestDto requestDto) {
        this.name = requestDto.getName();
        this.minOrderPrice = requestDto.getMinOrderPrice();
        this.deliveryFee = requestDto.getDeliveryFee();
    } // @PostMapping("/restaurant/register") 겨냥.
}
