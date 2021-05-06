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


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void ValueTypeEquals() {
        Address address1 = new Address("city", "street", "zipcode");
        Address address2 = new Address("city", "street", "zipcode");

        System.out.println(address1 == address2);
        System.out.println(address1.equals(address2));
    }

    private static void immutableType(EntityManager em) {
        Address address = new Address("city", "street", "zipcode");
        Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

        Member member1 = new Member();
        member1.setName("1");
        member1.setHomeAddress(address);
        member1.setPeriod(new Period());
        em.persist(member1);

        Member member2 = new Member();
        member2.setName("2");
        //member2.setHomeAddress(address);
        member2.setHomeAddress(copyAddress);
        member2.setPeriod(new Period());
        em.persist(member2);

        //임베디드 타입(값 타입)을 member1, member2 엔티티에서 공유함 -> 공유 참조 부작용 발생 -> setter 를 없앰 -> copyAddress 를 사용
        //member1.getHomeAddress().setCity("newCity");
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
