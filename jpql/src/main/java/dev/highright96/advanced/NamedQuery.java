package dev.highright96.advanced;

import dev.highright96.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

public class NamedQuery {

    public static void namedQuery(EntityManager em) {

        /*
        방법 1
        @Entity 에 @NamedQuery 추가.

        방법 2
        xml 파일에 추가

        Spring JPA 에서의 @NamedQuery 엔티티 코드를 너무 지저분하게 한다
        따라서, Spring Data JPA 에서 @Query 어노테이션으로 쉽고 깔끔하게 사용할 수 있다.
        */

        List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "member1")
                .getResultList();

        resultList.forEach(v -> System.out.println("v = " + v));
    }

}
