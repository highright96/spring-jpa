package dev.highright96.basic.ex1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
}
