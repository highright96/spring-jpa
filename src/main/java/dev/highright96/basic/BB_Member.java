package dev.highright96.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BB_Member {
    @Id @GeneratedValue
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "username", unique = true, length = 10)
    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) //ORDINAL 은 절대로 사용하면 안된다.
    private RoleType roleType;

    /*
    과거 버전
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    */

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    //varchar 을 넘어서는 큰 콘텐츠
    @Lob
    private String description;

    //db 와 연결하지 않음
    @Transient
    private int temp;
}
