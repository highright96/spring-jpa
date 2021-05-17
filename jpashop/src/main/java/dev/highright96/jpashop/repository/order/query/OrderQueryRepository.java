package dev.highright96.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders(); // query 1번 -> N번
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = getOrderItems(o.getOrderId()); //Query N번
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> getOrderItems(Long orderId) {
        return em.createQuery(
                "select new dev.highright96.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id , i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new dev.highright96.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.date, o.status, d.address) " +
                        "from Order o join o.member m " +
                        "join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
