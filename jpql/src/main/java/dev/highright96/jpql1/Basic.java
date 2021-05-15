package dev.highright96.jpql1;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Basic {

    public static void jpqlBasic(EntityManager em) {
        // 반환 타입이 확실
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

        // 반환 타입이 불확실
        Query query3 = em.createQuery("SELECT m.username, m.age from Member m");

        //파라미터 바인딩
        TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = :username", Member.class);
        query4.setParameter("username", "kim");
        Member singleResult = query4.getSingleResult();
        System.out.println("singleResult = " + singleResult);
    }

}
