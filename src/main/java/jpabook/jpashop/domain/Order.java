package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // table 선언
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") // db column 선언 PK
    private Long id;

    @ManyToOne // 다대일 , FK
    @JoinColumn(name = "member_id")
   private Member member;

    @OneToMany(mappedBy="order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderData; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER,CANCEL]

}
