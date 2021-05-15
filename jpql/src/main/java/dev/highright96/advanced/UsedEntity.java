package dev.highright96.advanced;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class UsedEntity {

    public static void usedEntity(EntityManager em) {

        /*
        엔티티 직접 사용 - 기본 키 값
        실행된 SQL : select * from Member m where m.id=?
        */

        // 파라미터로 엔티티 직접 전달
        String query1 = "select m from Member m where m = :member";
        List resultList1 = em.createQuery(query1)
                .setParameter("member", new Member())
                .getResultList();

        // 파라미터로 식별자 전달
        String query2 = "select m from Member m where m.id = :memberId";
        List resultList2 = em.createQuery(query2)
                .setParameter("memberId", 10L)
                .getResultList();
    }

}
