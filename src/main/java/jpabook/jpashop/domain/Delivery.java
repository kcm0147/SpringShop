package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name ="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery") // OneToOne의 경우 연관관계의 주인을 누구에게 둬도 상관없지만, 좀더 많이 사용하는 엔티티에 두었다.
    private Order order;

    @Embedded // 엔티티 내장 타
    private Address address;

    @Enumerated(EnumType.STRING) // enum을 사용할거면 @Enumerated를 넣어야한다  STRING => 숫자로 들어가는 것이 아니라 STRING으로 들어간다  ORDINAL => 1,2,3,4 로 들어가는
   private DeliveryStatus status; // READ,COMP
}
