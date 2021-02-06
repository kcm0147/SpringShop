package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;


    public void save(Order order) {
        em.persist(order);
    }

    public Order findOrder(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findOrders() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

//    public List<Order> findAll(OrderSearch orderSearch){ // 정적 쿼리
//
//
//
//         orderSearch에 값이 다 저장되어 있다는 가정 하에 밑의 쿼리문을 사용하면 되지만
//
//        em.createQuery("select o from Order o join o.member m"+
//                " where o.status = :status " +
//                " and m.name like :name ",Order.class)
//                .setParameter("status",orderSearch.getOrderStatus())
//                .setParameter("name",orderSearch.getMemberName())
//                .setMaxResults(1000) // paging 최대 1000
//                .getResultList();
//
//         o.member인 m과 Order를 join 한다
//
//    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" +
                    orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}


