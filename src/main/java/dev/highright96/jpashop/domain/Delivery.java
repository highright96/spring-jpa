package dev.highright96.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 기본으로 ORDINAL (숫자) 을 사용하므로, STRING 으로 바꿔줘야함
    private DeliveryStatus deliveryStatus;
}
