package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import java.util.List;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus; // [ORDER,CANCEL]






}
