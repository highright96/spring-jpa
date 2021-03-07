package dev.highright96.jpashop.service;

import dev.highright96.jpashop.domain.Address;
import dev.highright96.jpashop.domain.Member;
import dev.highright96.jpashop.domain.Order;
import dev.highright96.jpashop.domain.OrderStatus;
import dev.highright96.jpashop.domain.item.Book;
import dev.highright96.jpashop.domain.item.Item;
import dev.highright96.jpashop.exception.NotEnoughStockException;
import dev.highright96.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    Member member;
    Item item;

    @BeforeEach
    void init(){
        member = new Member();
        member.setName("테스트");
        member.setAddress(new Address("서울", "강동구", "123-1234"));
        em.persist(member);

        item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);
    }

    @Test
    void 상품주문() {
        //given
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //상품 주문시 상태는 ORDER
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        //주문한 상품 종류 수가 정확해야 한다.
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        //주문 가격은 가격 * 수량이다.
        assertThat(getOrder.getTotalPrice()).isEqualTo(item.getPrice() * orderCount);
        //주문 수량만큼 재고가 줄어야한다.
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        int orderCount = 11;

        //when, then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    void 주문취소() {
        //given
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        //주문 취소시 상태는 CANCEL 이다.
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        //주문이 최소된 상품은 그만큼 재고가 증가해야 한다.
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }
}