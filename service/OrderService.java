package com.sparta.fifthweek.service;

import com.sparta.fifthweek.dto.FoodOrderDto;
import com.sparta.fifthweek.dto.FoodOrderRequestDto;
import com.sparta.fifthweek.dto.OrderDto;
import com.sparta.fifthweek.dto.OrderRequestDto;
import com.sparta.fifthweek.model.Food;
import com.sparta.fifthweek.model.RequestOrder;
import com.sparta.fifthweek.model.ResponseOrder;
import com.sparta.fifthweek.model.Restaurant;
import com.sparta.fifthweek.repository.MenuRepository;
import com.sparta.fifthweek.repository.RegisterRepository;
import com.sparta.fifthweek.repository.RequestOrderRepository;
import com.sparta.fifthweek.repository.ResponseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MenuRepository MenuRepository;
    private final ResponseOrderRepository ResponseOrderRepository;
    private final RequestOrderRepository RequestOrderRepository;
    private final RegisterRepository RegisterRepository;

    // POST
    @Transactional
    public OrderDto registerOrder(OrderRequestDto requestDto) {
        // "출력하고 싶은 형태"를 return하겠다고 선언
        // Restaurant 때는 Restaurant 자체가 출력 형태와 동일해서 괜찮았지만 지금은 RequestOrder, ResponseOrder 분리해놓은 데다
        // ResponseOrder의 형태는 출력 형태와 동일하지 않기에 그렇게 할 수 없음.
        // 근데 POST, GET 사이에 repository에 저장해줘야 하는 것 아닌가?
        // repository에 RequestOrder, ResponseOrder별로 따로 저장을 해준 다음에
        // 거기에서 따로 짜맞춰서 OrderDto 형태를 정의해서 return 값으로 선언?

        Restaurant restaurant = RegisterRepository.findById(requestDto.getRestaurantId()).orElse(null);
        if (restaurant == null) {
            throw new NullPointerException("해당 음식점 정보가 존재하지 않습니다.");
        }

        List<RequestOrder> requests = new ArrayList<>();
        // quantity, deliveryFee, totalPrice(메뉴별 총합의 음식점별 총합 + 배달비)


        List<FoodOrderDto> foodResponseDto = new ArrayList<>();
        // food_name, quantity, price(메뉴별 총합)

        List<OrderDto> orderDtos = new ArrayList<>();
        OrderDto orderDto = new OrderDto();

        ResponseOrder resOrder = new ResponseOrder();

        // 전체 가격 계산용
        int initialPrice = 0;

        // List<FoodOrderRequestDto> 가 타겟
        for (FoodOrderRequestDto foodRequestDto : requestDto.getFoods()) {
            // 개별 : 전체
            // 안쪽 List에 있는 food에 관한 id, quantity 배열 1쌍 : 전체 request 형태에서 바라본 foods List
            // 조건문 party time.

            int quantity = foodRequestDto.getQuantity();
            if (quantity < 1 || quantity > 100 ) {
                throw new IllegalArgumentException("주문 가능한 식품 개수는 1개 이상 100개 이하입니다.");
            }

            Food food = MenuRepository.findById(foodRequestDto.getId()).orElseThrow(
                    () -> new NullPointerException("유효하지 않은 메뉴 정보입니다.")
            );
            //orElseThrow는 findAll쪽에는 사용 못하나?

            FoodOrderDto foDto = new FoodOrderDto(
                    food.getName(),
                    quantity,
                    food.getPrice() * quantity
            );
            // 여기서 생성자 필요

            initialPrice += foDto.getPrice();
            int deliveryFee = restaurant.getDeliveryFee();
            //totalPrice += deliveryFee; 이렇게 하니까 1번 더할 걸 2번 더해서 오류(for문이라서 그런 듯)
            int totalPrice = initialPrice + deliveryFee;
            // 이렇게 분리하는 것 말고도 다른 방식이 있겠지만 당장 생각나는 건 이것.

            // 최소주문가격보다 작으면 error 발생시키기.
            if (totalPrice < restaurant.getMinOrderPrice()) {
                throw new IllegalArgumentException("총 주문금액은 최소주문 요구비용을 능가해야 합니다.");
            }

            foodResponseDto.add(foDto);
            // FoodOrderDto 는 형태가 다 잡혔나?
//            restaurant = RegisterRepository.findById(food.getRestaurant().getId()).orElseThrow(
//                    () -> new NullPointerException("유효하지 않은 음식점 정보입니다.")
//            );

            orderDto = new OrderDto(
                    restaurant.getName(),
                    foodResponseDto,
                    deliveryFee,
                    totalPrice
            );

            orderDtos.add(orderDto);

            RequestOrder reqOrder = new RequestOrder(quantity, food, resOrder);
            // quantity는 column, food는 joincolumn
            // food는 좀더 구체적으로 말하자면 id 설정?

            RequestOrderRepository.save(reqOrder);
            requests.add(reqOrder);
            // RequestOrder는 RequestOrder의 id를 제외하고 quantity, food로 구성

            resOrder = new ResponseOrder(
                    restaurant.getDeliveryFee(),
                    totalPrice,
                   restaurant,
                   requests
                    );
           ResponseOrderRepository.save(resOrder);

        }
        return orderDto;
    }


    // GET
    // OrderDto return type이 Service 내에 반드시 있어야 하는 줄 몰랐음
    public List<OrderDto> getOrders() {
        return getOrder(ResponseOrderRepository.findAll());
    }

    private List<OrderDto> getOrder(List<ResponseOrder> responseOrderList) {
        List<OrderDto> result = new ArrayList<>();

        for (ResponseOrder responseOrder : responseOrderList) {
            List<FoodOrderDto> foodOrderDtos = getFoodResponseDtos(responseOrder);

            result.add(new OrderDto(
                    responseOrder.getRestaurant().getName(),
                    foodOrderDtos,
                    responseOrder.getDeliveryFee(),
                    responseOrder.getTotalPrice()
            ));
        }
        return result;
    }

    private List<FoodOrderDto> getFoodResponseDtos(ResponseOrder responseOrder) {
        List<FoodOrderDto> result = new ArrayList<>();

        List<RequestOrder> requestOrderList = RequestOrderRepository.findAllByResponseOrder(responseOrder);
        for ( RequestOrder requestOrder : requestOrderList ) {
            String name = requestOrder.getFood().getName();
            int quantity = requestOrder.getQuantity();
            int price = quantity * requestOrder.getFood().getPrice();
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