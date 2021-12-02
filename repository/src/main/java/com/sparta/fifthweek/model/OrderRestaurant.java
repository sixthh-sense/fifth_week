package com.sparta.fifthweek.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class OrderRestaurant {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity로 바꿔야 할 상황이 올지도?
    @Id
    private Long id;

    @Column
    private int totalPrice;

    @OneToOne(targetEntity = Restaurant.class) // fetch = FetchType.LAZY // ManyToOne을 맞게 설정한 건지 잘 모르겠다. // targetEntity = Restaurant.class
    @JoinColumn
    private Restaurant restaurant;

    @OneToMany(targetEntity = OrderFood.class, cascade = CascadeType.ALL)
    @JoinColumn
    private List<OrderFood> orderFoods = new ArrayList<>();
    // = new ArrayList<>(); 라고 선언해주는 건 있을지도 모르는 오류를 예방하기 위함? 자세한 원리는 모름.

    public OrderRestaurant(int totalPrice, Restaurant restaurant, List<OrderFood> orderFoods) {
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
        this.orderFoods = orderFoods;
    }
}
