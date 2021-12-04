package com.sparta.fifthweek.model;

import com.sparta.fifthweek.dto.FoodDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Food {

    //"음식(메뉴)"의 ID. GenerationType이 AUTO면 전체 DB 기준으로 id가 자동 증가하고, IDENTITY면 각 항목별로 증가하는 듯.
    // 난 Resopnse항목의 id를 보고 IDENTITY로 설정.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //@JsonIgnore 보이고 싶지 않은 항목 위에 붙이면 GET 쪽에 표시는 안 되는데(딱 2번까지는 풀 수 있음) Test까지 통과하기에 적절한 방법은 아닌 것 같다

    @ManyToOne(targetEntity = Restaurant.class) // fetch = FetchType.LAZY or EAGER 정확한 건 검색을 해야.
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
    private Restaurant restaurant;
    // Restaurant쪽에 OneToMany 눌렀더니(레스토랑 하나당 메뉴 리스트) 자동으로 생성됨
    // ManyToOne이 OneToMany에 우선되나? OneToMany를 생성했을 때는 빨간줄 뜨면서 ManyToOne 생성하라고 나오더니만 얘는 그런 게 없네. 아니면 다른 이유가 뭔가 있나?
    // 근데 이게 양쪽에 있으면 거울이 서로를 마주보는 것 같은 순환참조?라는 게 일어난다고 한다. 이런 용어는 처음 들어보네.
    // 이것도 항상 일어나는 게 아니라 특정 조건을 충족했을 대 일어나는 거라는데
    // 팀원분께 조언을 들은 바로는 Controller에서 바로 호출하는 형식이 아니면 괜찮다고 하셨던가....
    // 아무튼 조언을 들어도 완벽히는 이해하지 못함.

    //메뉴 이름
    @Column
    private String name;

    //메뉴 가격(개당 가격)
    @Column
    private int price;
    // 여기까지가 기본(#2번까지)

    public Food(FoodDto requestDto, Restaurant restaurant) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.restaurant = restaurant;
    }
}
