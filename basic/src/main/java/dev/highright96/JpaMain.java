package dev.highright96;

import dev.highright96.ex1.Child;
import dev.highright96.ex1.Parent;
import dev.highright96.ex2.Address;
import dev.highright96.ex2.AddressEntity;
import dev.highright96.ex2.Member;
import dev.highright96.ex2.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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

    private static void jqplIntro(EntityManager em) {
        // jpql
        em.createQuery(
                "select m from Member m where m.name like '%kim%'",
                Member.class
        ).getResultList();

            /* native query -> 영속성 컨텍스트에서 플러시가 일어나 db에 저장되어야하기 때문에 자동으로 flush 가 일어남.
            em.flush(); 자동
            */
        em.createNativeQuery("select MEMBER_ID, city, street from MEMBER").getResultList();
    }

    private static void valueTypeCollection(EntityManager em) {
        Member member = new Member();
        member.setName("member1");
        member.setHomeAddress(new Address("homeCity", "street", "10000"));

        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("족발");
        member.getFavoriteFoods().add("피자");

        member.getAddressHistory().add(new AddressEntity("old1", "street1", "10000"));
        member.getAddressHistory().add(new AddressEntity("old2", "street2", "10000"));

        em.persist(member);

        em.flush();
        em.clear();

        System.out.println("===============start1===============");
        Member findMember = em.find(Member.class, member.getId());

        /*
        값 타입 컬렉션 조회
        컬렉션은 지연로딩
         */
        List<AddressEntity> addressHistory = findMember.getAddressHistory();
        addressHistory.forEach(v -> System.out.println(v + " "));
        Set<String> favoriteFoods = findMember.getFavoriteFoods();
        favoriteFoods.forEach(v -> System.out.println(v + " "));

        /*
        값 타입 컬렉션 수정
         */
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().add("한식");

        /*
         equals 로 찾아서 제거해준다. *override 필수
         addressHistory 테이블을 memberId 의 모든 행을 삭제한 후 다시 insert
         */
        findMember.getAddressHistory().remove(new AddressEntity("old1", "street1", "10000"));
        findMember.getAddressHistory().add(new AddressEntity("newCity1", "street1", "10000"));
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
