package dev.highright96.basic;

import javax.persistence.EntityManager;
import java.util.List;

public class CaseExpression {

    public static void caseExpression(EntityManager em) {

        // 기본 CASE
        String query1 =
                "select " +
                        "case when m.age <= 10 then '학생요금'" +
                        "     when m.age >= 60 then '경로요금'" +
                        "     else '일반요금'" +
                        "end " +
                        "from Member m";
        List<String> resultList1 = em.createQuery(query1, String.class).getResultList();
        resultList1.forEach(v -> System.out.println("v = " + v));

        /*
         COALESCE
         이름이 null 이면 이름없는 회원을 반환
        */
        List<String> resultList2 = em.createQuery(
                "select coalesce(m.username, '이름없는 회원') from Member m", String.class)
                .getResultList();

        /*
        nullif
        이름이 관리자이면 null 을 봔한하고, 아니면 저장된 이름을 반환
        */
        List<String> resultList3 = em.createQuery(
                "select nullif(m.username, '관리자') from Member m", String.class)
                .getResultList();
    }
}
