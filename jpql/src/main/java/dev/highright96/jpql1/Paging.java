package dev.highright96.jpql1;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class Paging {

    public static void paging(EntityManager em) {
        List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(0) //조회 시작 위치
                .setMaxResults(10) //조회할 데이터 수
                .getResultList();

        //출력
        System.out.println("result size : " + resultList.size());
        resultList.forEach(m -> System.out.println("member=" + m));
    }

}
