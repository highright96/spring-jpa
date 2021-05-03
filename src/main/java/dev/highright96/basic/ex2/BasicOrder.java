package dev.highright96.basic.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class BasicOrder extends BasicBaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private BasicMember basicMember;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private BasicOrderStatus status;

    @OneToMany(mappedBy = "basicOrder")
    List<BasicOrderItem> orderItems = new ArrayList<>();

    public void changeBasicMember(BasicMember basicMember) {
        if (this.basicMember != null) {
            basicMember.getOrders().remove(this);
        }

        this.basicMember = basicMember;
        basicMember.getOrders().add(this);
    }

    public void addBasicOrderItem(BasicOrderItem basicOrderItem) {
        orderItems.add(basicOrderItem);
        basicOrderItem.setBasicOrder(this);
    }
}
