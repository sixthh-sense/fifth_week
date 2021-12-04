package com.sparta.fifthweek.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;
    // 개당 가격이 아니라 메뉴당 총비용 개념

    @OneToOne(fetch = FetchType.EAGER) // fetch = FetchType.LAZY // ManyToOne / OneToOne을 맞게 설정한 건지 잘 모르겠다. // targetEntity = Food.class
    @JoinColumn
    private Food food;

    public OrderFood(int quantity, int i, Food food) {
        this.quantity = quantity;
        this.price = i;
        this.food = food;
    }
    // OrderService에서 OrderFood에 관해 설정했을 때 생기게 된 생성자. int i는 자동으로 이름이 정해지던데 정확한 의미는 모름.

    public OrderFood(int quantity, Food food) {
        this.quantity = quantity;
        this.price = food.getPrice() * quantity;
        this.food = food;
    }
    // id, quantity 합쳐서 food 이룸
    // 실질적으로는 food 전체 값? DB 란에서는 Food의 id값을 가지고 있는 모양새(?)

    // Jackson이 JSON형식으로 변환 시도 : ResponseOrder안의 내용을 다 펼쳐서 보여주려고 함
    // 순환참조는 ResponseOrder 자체를 Controller에서 지칭할 때 일어날 것
    // 다른 순환참조 관련 사항은 K님 blog에 잘 정리되어 계심
}
