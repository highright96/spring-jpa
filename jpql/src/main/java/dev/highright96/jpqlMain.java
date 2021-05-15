package dev.highright96;

import dev.highright96.domain.Member;
import dev.highright96.domain.Team;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class jpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            init(em);;

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
        Team team1 = new Team();
        team1.setName("teamA");
        em.persist(team1);

        Team team2 = new Team();
        team2.setName("teamB");
        em.persist(team2);

        for (int i = 0; i < 5; i++) {
            Member member = new Member();
            member.setUsername("member" + i);
            member.setAge(10);
            member.changeTeam(team1);
            em.persist(member);
        }

        for (int i = 5; i < 11; i++) {
            Member member = new Member();
            member.setUsername("member" + i);
            member.setAge(20);
            member.changeTeam(team2);
            em.persist(member);
        }

        em.flush();
        em.clear();
    }
}
