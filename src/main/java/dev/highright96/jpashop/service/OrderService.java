package dev.highright96.jpashop.service;

import dev.highright96.jpashop.domain.Delivery;
import dev.highright96.jpashop.domain.Member;
import dev.highright96.jpashop.domain.Order;
import dev.highright96.jpashop.domain.OrderItem;
import dev.highright96.jpashop.domain.item.Item;
import dev.highright96.jpashop.repository.ItemRepository;
import dev.highright96.jpashop.repository.MemberRepository;
import dev.highright96.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 등록
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // JPA 의 강점, UPDATE SQL 작성이 필요없다. 트랜잭션 내의 엔티티 변화를 감지해 JPA 가 UPDATE 를 DB에 날려준다,
        order.cancel();
    }
}
