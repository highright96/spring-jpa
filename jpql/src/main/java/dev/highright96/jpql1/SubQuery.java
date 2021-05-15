package dev.highright96.jpql1;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class SubQuery {

    public static void subQuery(EntityManager em) {
        // 팀A 소속인 회원 조회
        List<Member> query1 = em.createQuery
                ("select m from Member m where exists ( select t from m.team t where t.name = '팀A')", Member.class)
                .getResultList();

        // 전체 상품 각각의 재고보다 주문량이 많은 주문들
        List<Member> query2 = em.createQuery
                ("select o from Orders o where o.orderAmount > ALL (select p.stockAmount from Product p)", Member.class)
                .getResultList();
    }

}
