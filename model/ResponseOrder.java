package com.sparta.fifthweek.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class ResponseOrder {
    // 강의 4-5, "조합"을 나타내는 Entity Class. OrderDto에 있는 항목을 기반으로 구성.
    // FoodOrderDto에 있는 food의 id항목이 좀 거슬리긴 한데 id라서 따로 설정해줄 필요... 있나?
    // 근데 Restaurant와 Order는 1대 다 관계로 설정하면 되지만(하나의 음식점에서 주문 여럿 받을 수 있다)
    // Food 와 Order는 뭔 관계로 설정해야 할지 애매하단 말이지. 다대 다? 아니면 Order의 구성으로 따져봤을 때 다대 일?
    // (하나의 주문은 여러 메뉴로 구성될 수 있다)

    @GeneratedValue(strategy = GenerationType.AUTO) // identity로 바꿔야 할 상황이 올지도?
    @Id
    private Long id;

//    @Column
//    private String restaurantName;
    // 가게 이름, Restaurant Entity에서 옴(name) - "굳이 입력할 필요가 없다고 하니" 주석 처리.

//    @Column
//    private String name;
    // 메뉴 이름, Food Entity에서 옴(name)

//    @Column
//    private int quantity;
//     얘만 이 Entity의 오리지널 column?
    // RequestOrder에 썼으니까 지워도 됨(?)

//    @Column
//    private int price; // RequestOrder에 저장할 것
    // "메뉴당 총합" 개념(Food의 price * PurchaseOrder의 quantity 곱)

    @Column
    private int deliveryFee;
    // 배달료, Restaurant Entity에서 옴 -> 사실 얘도 필요가 없나?

    @Column
    private int totalPrice;
    // "메뉴당 총합"의 총합 + deliveryFee

    @OneToMany(targetEntity = RequestOrder.class)
    @JoinColumn(nullable = false) // name="FOOD_ID", 뺌
    private List<RequestOrder> foods = new ArrayList<>();
    // 이건 왜 새로운 ArrayList로 둔 걸까?
    // 설정을 안 해도 되나??? 자동으로 들어간다??
    // ManyToOne이면 데이터 신경 쓰기?
    // OneToMany면 자동으로 채워져서 데이터 신경 덜 써도 ok?

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(nullable = false) // name="RESTAURANT_ID", 넣었다가 필요가 없나 싶어서 뺌
    private Restaurant restaurant;
    // responseOrder를 생성하면서 Restaurant를 정의?

//    public ResponseOrder(FoodOrderDto foodOrder, Food food) {
//        this.name = foodOrder.getName();
//        this.quantity = foodOrder.getQuantity();
//        this.price = food.getPrice() * this.quantity;
//        //this.price = foodOrder.getPrice();
//    }

//    public ResponseOrder(Food food, int quantity) {
//        this.foods = (List<Food>) food;
//        this.quantity = quantity;
//        this.price = food.getPrice() * quantity;
//    }
//    public ResponseOrder(Restaurant restaurant, int deliveryFee, int price) {
//        this.restaurant = restaurant;
//        this.deliveryFee = deliveryFee;
//        this.price = price;
//        this.totalPrice = deliveryFee + price;
//        // 정확히 하면 List 내 price들의 총합인데 어떻게 표현해야 할지 모르겠음.
//    } // totalPrice, "총합" price에 대한 설명은 Service에서 작성할 것.

    @Builder
    public ResponseOrder(
           // int quantity,
            int deliveryFee,
            int totalPrice,
            Restaurant restaurant,
            List<RequestOrder> foods
            // food id, quantity 구성
    ) {
       // this.quantity = quantity;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
        this.foods = foods;
    }

    public ResponseOrder(int deliveryFee, int totalPrice) {
       // this.quantity = quantity;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
    }


    public ResponseOrder(int deliveryFee, int totalPrice, Restaurant restaurant) {
        //this.quantity = quantity;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
    }
}
