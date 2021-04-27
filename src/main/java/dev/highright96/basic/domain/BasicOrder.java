package dev.highright96.basic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BasicOrder {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private BasicOrderStatus status;
}
