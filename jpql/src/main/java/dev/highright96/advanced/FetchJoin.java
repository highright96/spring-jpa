package dev.highright96.advanced;

import dev.highright96.domain.Member;
import dev.highright96.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

public class FetchJoin {

    public static void fetchJoin(EntityManager em) {
        /*
        조인 사용 x
        회원0, 팀A (SQL)
        회원1, 팀A (1차캐치 - 영속성 컨텍스트)
        회원2, 팀A (1차캐치 - 영속성 컨텍스트)
        ...
        회원5, 팀B (SQL)
        회원6, 팀B (1차캐치 - 영속성 컨텍스트)
        ...
        */
        String query1 = "select m from Member m";
        List<Member> resultList1 = em.createQuery(query1, Member.class).getResultList();
        resultList1.forEach(v -> System.out.println("member = " + v + " ||| team = " + v.getTeam().getName()));


        System.out.println("===========================================");


        /*
        페치 조인
        한방 쿼리로 멤버, 팀의 정보가 모두 들어가있음
        위와 같이 추가적인 쿼리가 발생하지 않음
        */
        String query2 = "select m from Member m join fetch m.team t";
        List<Member> resultList2 = em.createQuery(query2, Member.class).getResultList();
        resultList2.forEach(v -> System.out.println("member = " + v + " ||| team = " + v.getTeam().getName()));


        System.out.println("===========================================");


        /*
        일대다 관계 페치 조인
        일대다 관계에서 조인을 할 때에는 중복 제거를 해줘야함. (Distinct 사용)
        */
        String query3 = "select distinct t from Team t join fetch t.members";
        List<Team> resultList3 = em.createQuery(query3, Team.class).getResultList();
        resultList3.forEach(v -> System.out.println("team = " + v.getName() + " ||| members = " + v.getMembers().size()));

        /*
        페치 조인과 일반 조인의 차이
        일반 조인 : 연관된 엔티티를 조회하지 않음. 따라서 연관 엔티티를 조회할 때 추가 쿼리가 나감
        페치 조인 : 연관된 엔티티를 함께 조회함. 따라서 추가 쿼리 발생x
        동일한 점 : 쿼리는 동일함. select 부분 제외
        */
        em.flush();
        em.clear();
        String query5 = "select m from Member m join m.team t";
        List<Member> resultList5 = em.createQuery(query5, Member.class).getResultList();
        resultList5.forEach(v -> System.out.println("member = " + v.getUsername() + " ||| team = " + v.getTeam().getName()));
    }

}
