package dev.highright96.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 연관관계의 주인, FK 와 연결시켜줌
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //cascade -> persist 를 전파한다. 각각 해줄필요가 없음.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // ORDER 와 DELIVERY 는 1대1 관계이다. 어느곳에 연관관계주인이 가능하지만 자주 조회하는 엔티티를 선택하는 것이 좋다.

    @Column(name = "order_date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    /*
    ==연관관계 메서드==//
    양방향 관계일때 사용, 연관관계 메서드의 위치는 양쪽중에 핵심적으로 컨트롤하는쪽, 아래와 같은 역활을 수행
    member.getOrders().add(order);, order.setMember(member);
    */
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void setOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.setOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        orderItems.forEach(OrderItem::cancel);
    }

    //==조회 로직==/
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
