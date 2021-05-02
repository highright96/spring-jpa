package dev.highright96.basic.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BasicOrderItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private BasicOrder basicOrder;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private BasicItem basicItem;

    private Integer orderPrice;
    private Integer count;

}
