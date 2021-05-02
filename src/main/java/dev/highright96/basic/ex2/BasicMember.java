package dev.highright96.basic.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BasicMember {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "basicMember")
    private List<BasicOrder> orders = new ArrayList<>();

    @Column(length = 10) //이와 같이 제약 조건은 사용하는 것이 좋다.
    private String name;

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private BasicDelivery basicDelivery;
}
