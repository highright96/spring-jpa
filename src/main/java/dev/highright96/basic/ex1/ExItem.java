package dev.highright96.basic.ex1;

import javax.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //단일 테이블 전략
@DiscriminatorColumn //자식 테이블을 구분하는 구분 컬럼 추가(Default = DTYPE)
public class ExItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

}
