package dev.highright96;

import dev.highright96.jpql.Address;
import dev.highright96.jpql.Member;
import dev.highright96.jpql.MemberDTO;
import dev.highright96.jpql.Team;

import javax.persistence.*;
import java.util.List;

public class jpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            init(em);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void init(EntityManager em) {
        Member member = new Member();
        member.setUsername("member1");
        member.setAge(10);
        em.persist(member);
    }

    private static void projection(EntityManager em) {
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
        스카라 타입 프로젝션
        고민 : 타입이 두개인데 어떻게 받지?
        */

        // 방법1
        List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
                .getResultList();

        Object[] o = resultList.get(0);
        System.out.println("username : " + o[0]);
        System.out.println("age : " + o[1]);

        // 방법2(추천)
        List<MemberDTO> memberDTOS = em.createQuery("select new dev.highright96.jpql.MemberDTO(m.username, m.age) from Member m")
                .getResultList();

        MemberDTO memberDTO = memberDTOS.get(0);
        System.out.println(memberDTO.toString());
    }

    private static void jpqlBasic(EntityManager em) {
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
