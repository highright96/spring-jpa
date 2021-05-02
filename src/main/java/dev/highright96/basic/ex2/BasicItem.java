package dev.highright96.basic.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BasicItem {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long Id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
}
