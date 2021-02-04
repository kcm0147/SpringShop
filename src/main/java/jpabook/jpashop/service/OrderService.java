package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Long order(Long memberId,Long itemId,int count){


        Member member= memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order); // cascade Type으로 인해 나머지 delivery,order등이 다 저장이됨

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOrder(orderId);
        order.cancel(); // jpa의 강점으로 order의 count값이 작아지면 자동으로 JPA가 query를 이용하여 데이터베이스에 자동으로 적용을 시켜준다.
        //cancel() 뿐만 아니라 entity와 관련된 모든 디비값들은 전부 다!!
   }
}
