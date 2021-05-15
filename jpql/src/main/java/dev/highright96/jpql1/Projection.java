package dev.highright96.jpql1;

import dev.highright96.domain.Address;
import dev.highright96.domain.Member;
import dev.highright96.domain.MemberDTO;
import dev.highright96.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Projection {

    public static void projection(EntityManager em) {
        /*
        쿼리 결과를 엔티티로 받은 엔티티 프로젝션은 영속성 컨텍스트에서 관리된다.
        엔티티 타입 프로젝션
        */
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        List<Member> result = query1.getResultList();
        Member findMember = result.get(0);
        findMember.setAge(20);

        TypedQuery<Team> badQuery = em.createQuery("select m.team from Member m", Team.class);
        TypedQuery<Team> bestQuery = em.createQuery("select t from Member m join Team t", Team.class);

        // 임베디드 타입 프로젝션
        em.createQuery("select o.address from Orders o", Address.class).getResultList();

        /*
        스캃라 타입 프로젝션
        고민 : 타입이 두개인데 어떻게 받지?
        */

        // 방법1
        List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
                .getResultList();

        Object[] o = resultList.get(0);
        System.out.println("username : " + o[0]);
        System.out.println("age : " + o[1]);

        // 방법2(추천)
        List<MemberDTO> memberDTOS = em.createQuery("select new dev.highright96.jpql1.MemberDTO(m.username, m.age) from Member m")
                .getResultList();

        MemberDTO memberDTO = memberDTOS.get(0);
        System.out.println(memberDTO.toString());
    }

}
