package dev.highright96.jpashop.domain;

import dev.highright96.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    // 객체간의 관계에서는 양방향 맵핑이 가능하지만 관계형 데이터베이스에서는 양방향 관계가 불가능해 중간에 조인테이블(일대다, 다대일로 풀어낼 수 있는)을 둬야한다.
    // 하지만 조인테이블에 필드를 추가할 수 가 없다. 단순한 테이블을 실무에 쓰이지않는다.
    private List<Item> items = new ArrayList<>();


    // 카테고리의 구조, 부모와 자식관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category category) {
        this.getChild().add(category);
        category.setParent(this);
    }
}
