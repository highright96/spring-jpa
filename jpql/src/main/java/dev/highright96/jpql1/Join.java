package dev.highright96.jpql1;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class Join {

    public static void join(EntityManager em) {
        // 내부 조인
        List<Member> resultList1 = em.createQuery("select m from Member m inner join m.team t", Member.class).getResultList();

        // 외부 조인
        List<Member> resultList2 = em.createQuery("select m from Member m left outer join m.team t", Member.class).getResultList();

        // 세타 조인(카티션곱)
        List<Member> resultList3 = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class).getResultList();

        /*
            연관관계 없는 엔티티 외부 조인
            m.team_id = t.id 가 없음
        */
        List<Member> resultList4 = em.createQuery("select m from Member m left join Team t on m.username = t.name", Member.class).getResultList();
    }
}
