package dev.highright96.basic.ex1;

import javax.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

}
