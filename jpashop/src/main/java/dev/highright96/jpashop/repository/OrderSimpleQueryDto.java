package dev.highright96.jpashop.repository;

import dev.highright96.jpashop.domain.Address;
import dev.highright96.jpashop.domain.Order;
import dev.highright96.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime localDateTime, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.localDateTime = localDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    /*
    public OrderSimpleQueryDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.localDateTime = order.getDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
    }
    */
}
