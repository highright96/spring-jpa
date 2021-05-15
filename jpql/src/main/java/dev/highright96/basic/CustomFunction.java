package dev.highright96.basic;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomFunction {

    public static void customFunction(EntityManager em) {
        // Concat 문자열 합치기
        String query1 = "select concat('a', 'b') from Member m";
        List<String> resultList1 = em.createQuery(query1, String.class).getResultList();
        resultList1.forEach(v -> System.out.println("v = " + v));

        // locate : 문자 찾기
        String query2 = "select locate('a', 'bbab') from Member m";
        List<Integer> resultList2 = em.createQuery(query2, Integer.class).getResultList();
        resultList2.forEach(v -> System.out.println("v = " + v));

        // 사용자 정의 함수
        String query3 = "select function('group_concat', m.username) from Member m";
        List<String> resultList3 = em.createQuery(query3, String.class).getResultList();
        resultList3.forEach(v -> System.out.println("v = " + v));
    }

}
