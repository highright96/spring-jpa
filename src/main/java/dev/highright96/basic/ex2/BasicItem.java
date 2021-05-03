package dev.highright96.basic.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class BasicItem extends BasicBaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long Id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
}
