package dev.highright96;

import dev.highright96.domain.Member;
import dev.highright96.domain.Team;

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
        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        for (int i = 0; i < 1; i++) {
            Member member = new Member();
            member.setUsername("member" + i);
            member.setAge(i);
            member.changeTeam(team);
            em.persist(member);
        }
    }
}
