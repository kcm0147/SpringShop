package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // Entity Manager를 자동으로 생성해주는 어노테이
    private EntityManager em;

    public Long save(Member member){
        em.persist(member); // member 객체를 Entity manger를 통해서 등
        return member.getId();
    }
    public Member find(Long id){
        return em.find(Member.class,id);
    }
}
