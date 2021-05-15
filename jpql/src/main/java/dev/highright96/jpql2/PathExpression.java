package dev.highright96.jpql2;

import javax.persistence.EntityManager;

public class PathExpression {

    public static void pathExpression(EntityManager em) {

        // m.username => 상태필드
        String query1 = "select m.username from Member m";
        em.createQuery(query1, String.class).getResultList();

        /*
        m.team => 단일 값 연관 필드 => 묵시적 내부 조인 발생, 탐색 가능
        */
        String query2 = "select m.team.name from Member m";
        em.createQuery(query2, String.class).getResultList();

        /*
        t.members => 컬렉션 값 연관 경로 => 묵시적 내부 조인 발생, 탐색X(컬렉션이기 때문에)
        */
        String query3 = "select t.members.size from Team t";
        Integer singleResult = em.createQuery(query3, Integer.class).getSingleResult();
        System.out.println("singleResult = " + singleResult);
    }

}
