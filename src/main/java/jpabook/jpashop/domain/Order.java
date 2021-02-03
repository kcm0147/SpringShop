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

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 , FK
    @JoinColumn(name = "member_id")
   private Member member;

    @OneToMany(mappedBy="order",cascade = CascadeType.ALL) // order가 persist되면, 자동으로 orderItemA,B,C등이 다 자동으로 persist 된다
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderData; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER,CANCEL]


    //== 연관관계 메서드==// -- 양방향 연관관계, 보통은 컨트롤 하는쪽이 들고 있다

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery= delivery;
        delivery.setOrder(this);
    }




}
