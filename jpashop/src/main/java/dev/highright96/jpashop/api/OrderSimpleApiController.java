package dev.highright96.jpashop.api;

import dev.highright96.jpashop.domain.Address;
import dev.highright96.jpashop.domain.Order;
import dev.highright96.jpashop.domain.OrderStatus;
import dev.highright96.jpashop.repository.OrderRepository;
import dev.highright96.jpashop.repository.OrderSearch;
import dev.highright96.jpashop.repository.OrderSimpleQueryDto;
import dev.highright96.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ManyToOne, OneToOne 에서의 성능 최적화
 * Order
 * Order -> Member : 다대일
 * Order -> Delivery : 일대일
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * V1. 엔티티를 직접 반환
     * JSON 으로 파싱하면서 양방향 연관관계를 계속 타고 들어가 무한 루프에 빠지게 된다.
     * 해결방법
     * 반대쪽에 @JsonIgnore 을 사용한다. 그러나 지연로딩이기 때문에 프록시 객체를 갖고 있어서 exception 이 일어남.
     * 결론은 다른 방법을 쓰자.
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return orders;
    }


    /**
     * V2. 엔티티를 조회해서 DTO 로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     * - N + 1 문제
     * - Order 조회 1번, 결과 2(N)개
     * - Order -> Member  N(2)번 조회
     * - Order -> Delivery  N(2)번 조회
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return SimpleOrderDto.getInstance(orders);
    }

    /**
     * V3. 페치 조인을 사용해 쿼리를 한 번만 보냄
     * 페치 조인으로 order -> member , order -> delivery 객체 그래프 이동에 추가 쿼리가 나가지 않는다(지연 로딩 X)
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> findOrders = orderRepository.findAllWithMemberDelivery();
        return SimpleOrderDto.getInstance(findOrders);
    }

    @Data
    @AllArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime localDateTime;
        private OrderStatus orderStatus;
        private Address address;

        public static List<SimpleOrderDto> getInstance(List<Order> orders) {
            return orders.stream()
                    .map(o -> new SimpleOrderDto(
                                    o.getId(),
                                    o.getMember().getName(),
                                    o.getDate(),
                                    o.getStatus(),
                                    o.getDelivery().getAddress()
                            )
                    )
                    .collect(Collectors.toList());
        }
    }

    /**
     * V4. JPA 에서 DTO 로 바로 조회 (통계성 데이터를 조회할때 많이 사용)
     * 장점 : 일반적인 SQL 을 사용할 때 처럼 select 절에서 원하는 데이터만 선택해서 조회 (네트워크 용량 최적화 생각보다 미비)
     * 단점 : 리포지토리 재사용성 떨어짐, 하나의 API 스펙(화면)에만 맞는 코드임, simple query 와 같은 패키지로 따로 관리하는 것이 좋음.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }
}
