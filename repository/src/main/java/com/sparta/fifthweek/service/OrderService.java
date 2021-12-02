package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.FoodOrderDto;
import com.sparta.fifthweek.dto.FoodOrderRequestDto;
import com.sparta.fifthweek.dto.OrderDto;
import com.sparta.fifthweek.dto.OrderRequestDto;
import com.sparta.fifthweek.model.Food;
import com.sparta.fifthweek.model.OrderFood;
import com.sparta.fifthweek.model.OrderRestaurant;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.FoodRepository;
import com.sparta.fifthweek.repository.OrderFoodRepository;
import com.sparta.fifthweek.repository.OrderRestaurantRepository;
import com.sparta.fifthweek.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final FoodRepository FoodRepository;
    private final OrderRestaurantRepository OrderRestaurantRepository;
    private final OrderFoodRepository OrderFoodRepository;
    private final RestaurantRepository RestaurantRepository;

    // POST
    @Transactional
    public OrderDto registerOrder(OrderRequestDto requestDto) {
        // "출력하고 싶은 형태"를 return하겠다고 선언
        // Restaurant 때는 Restaurant 자체가 출력 형태와 동일해서 괜찮았지만 지금은 RequestOrder, ResponseOrder 분리해놓은 데다
        // ResponseOrder의 형태는 출력 형태와 동일하지 않기에 그렇게 할 수 없음.
        // 근데 POST, GET 사이에 repository에 저장해줘야 하는 것 아닌가?
        // repository에 RequestOrder, ResponseOrder별로 따로 저장을 해준 다음에
        // 거기에서 따로 짜맞춰서 OrderDto 형태를 정의해서 return 값으로 선언?

        Restaurant restaurant = RestaurantRepository.findById(requestDto.getRestaurantId()).orElse(null);
        if (restaurant == null) {
            throw new NullPointerException("해당 음식점 정보가 존재하지 않습니다.");
        }

        List<OrderFood> requests = new ArrayList<>();
        // quantity, deliveryFee, totalPrice(메뉴별 총합의 음식점별 총합 + 배달비)
        OrderFood reqOrder = new OrderFood();
        //List<OrderRestaurant> responses = new ArrayList<>();
        OrderRestaurant resOrder = new OrderRestaurant();
        List<FoodOrderDto> foodOrderDtos = new ArrayList<>();
        // food_name, quantity, price(메뉴별 총합)
        //List<OrderDto> orderDtos = new ArrayList<>();
        OrderDto orderDto = new OrderDto();

        // 전체 가격 계산용
        int initialPrice = 0;

        // List<FoodOrderRequestDto> 가 타겟
        for (FoodOrderRequestDto foodRequestDto : requestDto.getFoods()) {
            // 개별 : 전체
            // 안쪽 List에 있는 food에 관한 id, quantity 배열 1쌍 : 전체 request 형태에서 바라본 foods List(배열들의 집합)
            // {(food id: 123, food quantity: 234), (food id: 123, food quantity: 234), (food id: 123, food quantity: 234), ... }식.

            // 조건문 party time.

            int quantity = foodRequestDto.getQuantity();
            if (quantity < 1 || quantity > 100 ) {
                throw new IllegalArgumentException("주문 가능한 식품 개수는 1개 이상 100개 이하입니다.");
            }

            Food food = FoodRepository.findById(foodRequestDto.getId()).orElseThrow(
                    () -> new NullPointerException("유효하지 않은 메뉴 정보입니다.")
            );
            //orElseThrow는 findAll쪽에는 사용 못하나?

            FoodOrderDto foodOrderDto = new FoodOrderDto(
                    food.getName(),
                    quantity,
                    food.getPrice() * quantity
            );
            // 여기서 생성자 필요

            initialPrice += foodOrderDto.getPrice();
            int deliveryFee = restaurant.getDeliveryFee();
            //totalPrice += deliveryFee; 이렇게 하니까 1번 더할 걸 2번 더해서 오류(for문이라서 그런 듯)

            // 이렇게 분리하는 것 말고도 다른 방식이 있겠지만 당장 생각나는 건 이것.

            // 최소주문가격보다 작으면 error 발생시키기.
            if (initialPrice < restaurant.getMinOrderPrice()) {
                throw new IllegalArgumentException("총 주문금액은 최소주문 요구비용을 능가해야 합니다.");
            }
            int totalPrice = initialPrice + deliveryFee;

            foodOrderDtos.add(foodOrderDto);
            // FoodOrderDto 는 형태가 다 잡혔나?
//            restaurant = RegisterRepository.findById(food.getRestaurant().getId()).orElseThrow(
//                    () -> new NullPointerException("유효하지 않은 음식점 정보입니다.")
//            );
            orderDto = new OrderDto(
                    restaurant.getName(),
                    foodOrderDtos,
                    deliveryFee,
                    totalPrice
            );

            reqOrder = new OrderFood(
                    quantity,
                    food.getPrice() * quantity,
                    food
                    );
            // quantity는 column, food는 joincolumn
            // food는 좀더 구체적으로 말하자면 id 설정?

            requests.add(reqOrder);
            // List<OrderFood> requests = new ArrayList<>();

            resOrder = new OrderRestaurant(
                    totalPrice,
                    restaurant,
                    requests
            );
        }

        OrderFoodRepository.save(reqOrder);
        OrderRestaurantRepository.save(resOrder);

        return orderDto;
    }


    // GET
    // OrderDto return type이 Service 내에 반드시 있어야 하는 줄 몰랐음
    public List<OrderDto> getOrders() {

        return getOrder(OrderRestaurantRepository.findAll());
    }

    private List<OrderDto> getOrder(List<OrderRestaurant> orderRestaurantList) {
        List<OrderDto> result = new ArrayList<>();

        for (OrderRestaurant orderRestaurant : orderRestaurantList) {
            List<FoodOrderDto> foodOrderDtos = getFoodResponseDtos(orderRestaurant);

            result.add(new OrderDto(
                    orderRestaurant.getRestaurant().getName(),
                    foodOrderDtos,
                    orderRestaurant.getRestaurant().getDeliveryFee(),
                    orderRestaurant.getTotalPrice()
            ));
        }
        return result;
    }

    private List<FoodOrderDto> getFoodResponseDtos(OrderRestaurant orderRestaurant) {
        List<FoodOrderDto> result = new ArrayList<>();

        List<OrderFood> orderFoodList = orderRestaurant.getOrderFoods();
                //OrderFoodRepository.findAll();
        for (OrderFood orderFood : orderFoodList) {
            String name = orderFood.getFood().getName();
            int quantity = orderFood.getQuantity();
            int price = orderFood.getPrice();
            result.add(new FoodOrderDto(name, quantity, price));
        }
        return result;
    }
}



//        List<OrderDto> request = new ArrayList<>();
//
//        for (ResponseOrder purchase : purchases) {
//            request.add(orderDtoSetting(purchase));
//        }
//
//        return request;
//    }
//
//    private OrderDto orderDtoSetting(ResponseOrder purchase) {
//        return new OrderDto(
//                purchase.getRestaurantName(),
//                purchase,
//                purchase.getDeliveryFee(),
//                purchase.getTotalPrice()
//        );
//    }