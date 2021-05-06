package dev.highright96;

import dev.highright96.ex1.Child;
import dev.highright96.ex1.Parent;
import dev.highright96.ex2.Address;
import dev.highright96.ex2.Member;
import dev.highright96.ex2.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setHomeAddress(new Address("city", "street", "zipcode"));
            member.setPeriod(new Period());
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void cascade(EntityManager em) {
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
        /*
        no-cascade
        em.persist(child1);
        em.persist(child2);
        */
        em.flush();
        em.clear();

        Parent findParent = em.find(Parent.class, parent.getId());
        findParent.getChildList().remove(0);
        em.persist(findParent);
    }
}
