package dev.highright96.jpashop.repository.order.simplequery;


import dev.highright96.jpashop.repository.OrderRepository;
import dev.highright96.jpashop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new dev.highright96.jpashop.repository.OrderSimpleQueryDto" +
                        "(o.id, m.name, o.date, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

}
