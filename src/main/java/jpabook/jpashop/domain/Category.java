package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
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
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 다대다 관계는 일대다 다대일 관계로 풀어주어야 하기때문에 중간 Table이 필요로 한다. 중요..
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY) // 카테고리 구조 상 상위계층 하위계층 카테고리가 존재하기 떄문에 prent, child 선언
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy= "parent") // 카테고리 구조 상 하위계층은 여러개가 존재할 수 있기 떄문에 List 선언
    private List<Category> child;


    // 연관관계 메서드 //
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
