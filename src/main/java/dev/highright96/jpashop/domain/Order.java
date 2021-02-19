package dev.highright96.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_Id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // ORDER 와 DELIVERY 는 1대1 관계이다. 어느곳에 연관관계주인이 가능하지만 자주 조회하는 엔티티를 선택하는 것이 좋다.

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
