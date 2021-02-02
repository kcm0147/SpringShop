package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계의 주인이 Order의 변수이름인 member로 설정한다. 여기에 값을 수정한다해도 값이 변하지 않는다 Order의 member 객체의 값을 변경해야 값이 변경된
    private List<Order> orderList= new ArrayList<>();

}
