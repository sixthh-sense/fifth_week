package com.sparta.fifthweek.model;

import com.sparta.fifthweek.dto.FoodDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Food {

    //"음식(메뉴)"의 ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //@JsonIgnore 이렇게 하면 GET 쪽에 표시는 안 되는데(딱 2번까지는 풀 수 있음) Test까지 통과하기에 적절한 방법은 아닌 것 같다
    //"음식점"의 ID
//    @Column(nullable=false)
//    private Long restaurantId;
    @ManyToOne(targetEntity = Restaurant.class) // fetch = FetchType.LAZY
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
    private Restaurant restaurant;
    // Restaurant쪽에 OneToMany 눌렀더니(레스토랑 하나당 메뉴 리스트) 지가 알아서 생성됨
    // ManyToOne이 OneToMany에 우선되나? OneToMany를 생성했을 때는 빨간줄 뜨면서 ManyToOne 생성하라고 나오더니만 얘는 그런 게 없네. 아니면 다른 이유가 뭔가 있나?
    // 근데 이게 양쪽에 있으면 거울이 서로를 마주보는 것 같은 순환참조?라는 게 일어난다고 한다. 이런 용어는 처음 들어보네.

    //메뉴 이름
    @Column
    private String name;

    //메뉴 가격(개당 가격)
    @Column
    private int price;
    // 여기까지가 기본(#2번까지)

//    @Column
//    private int quantity;
//    // #3 quantity 항목 추가?
    // quantity 항목을 아마 Food Entity에 추가하게 될 텐데,

    public Food(FoodDto requestDto, Restaurant restaurant) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.restaurant = restaurant;
    }
    // restaurantId가 restaurant table의 primary key를 말하는 거라고 알아들을 수 있을까? 이것만으론 불안
    // controller에서 정의해주는 걸로 ok일까?

//    public Food(FoodDto requestDto) {
//        this.name = requestDto.getName();
//        this.price = requestDto.getPrice();
//       // this.restaurantId = requestDto.getRestaurantId();
//    }

    // #2 RequestBody가 단순 1개 입력하는 게 아니라 "배열"을 입력해야 하는 것.

    // 여기도 Builder추가할까?
}
