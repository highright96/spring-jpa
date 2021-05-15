package dev.highright96.advanced;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class BulkQuery {

    public static void bulkQuery(EntityManager em) {

        List<Member> resultList = em.createQuery("select m from Member m", Member.class).getResultList();

        // age = 10, 20
        resultList.forEach(v -> System.out.println("member age = " + v.getAge()));

        /*
        em.flush 자동 호출 후 update 쿼리가 실행된다.
        영속성 컨텍스트를 무시하고 바로 DB 에 update 쿼리를 보낸다. 따라서, 영속성 컨텍스트에는 member 의 age 가 30으로 업데이트 되지 않았다.
        떠라서 벌크 연산 수행 후 영속성 컨텍스트를 초기화를 해야해서 db 와 영속성 컨텍스트와의 데이터 버전을 맞춰야 한다.
        */
        int result = em.createQuery("update Member m set m.age = 30")
                .executeUpdate();

        // 결과 : 10
        Member unValidMember = em.find(Member.class, resultList.get(0).getId());
        System.out.println("unValidMember = " + unValidMember.getAge());

        em.clear();

        // 결과 : 30
        // 영속성 컨텍스트를 비워줘 최신화 된 멤버 데이터를 가져온다.
        Member validMember = em.find(Member.class, resultList.get(0).getId());
        System.out.println("validMember = " + validMember.getAge());
    }

}
