package dev.highright96.basic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BasicOrder {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    //@Column(name = "MEMBER_ID")
    //private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private BasicMember basicMember;

    private LocalDateTime orderDate; //ORDER_DATE, order_date

    @Enumerated(EnumType.STRING)
    private BasicOrderStatus status;

    public void changeBasicMember(BasicMember basicMember) {

        if(this.basicMember != null){
            basicMember.getOrders().remove(this);
        }

        this.basicMember = basicMember;
        basicMember.getOrders().add(this);
    }
}
