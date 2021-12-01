package com.sparta.fifthweek.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class RequestOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
    // id, quantity 합쳐서 food 이룸
    // 실질적으로는 food 전체 값? DB 란에서는 Food의 id값을 가지고 있는 모양새(?)

    @ManyToOne(targetEntity = ResponseOrder.class)
    @JoinColumn(nullable = false)
    private ResponseOrder responseOrder;
    // 이거 순환참조 일어나는 게 아닌가 싶다. 양성은님께 물어볼까?
    // 구조 이해는 덜한 상태지만 "회색"으로 칠해진 걸 봐서는 이쪽이 수상.
    // DB 강의를 듣고 난 이후엔 마음이 변할 수도 있지만 일단 이쪽을 주석처리하자.
    // Jackson이 JSON형식으로 변환 시도 : ResponseORder안의 내용을 다 펼쳐서 보여주려고 함
    // 순환참조는 ResponseOrder 자체를 Controller에서 지칭할 때 일어날 것
    // 다른 순환참조 관련 사항은 김용빈님 blog에 잘 정리되어 계심

    @Builder
    public RequestOrder(int quantity, Food food, ResponseOrder resOrder) {
        this.quantity = quantity;
        this.food = food;
        this.responseOrder = resOrder;
    }

    public RequestOrder(int quantity, Food food) {
        this.quantity = quantity;
        this.food = food;
    }
}
