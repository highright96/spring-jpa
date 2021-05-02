package dev.highright96.basic.ex2;

import javax.persistence.*;

@Entity
public class BasicDelivery {

    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private BasicDeliveryStatus status;

    @OneToOne(mappedBy = "basicDelivery")
    private BasicMember basicMember;
}
