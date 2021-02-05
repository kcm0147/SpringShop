package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;


    @Test
    public void order() throws Exception{
        //given
        Member member = createMember("회원1");

        Book book = createBook("JPA", 12000, 10);


        int orderCount=2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //then
        Order getOrder = orderRepository.findOrder(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 12000*orderCount,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한", 8,book.getStockQuantity());

    }



    @Test
    public void cancel () throws Exception{
        //given
        Member member = createMember("mook");
        Book curBook = createBook("new JPA",12000,11);

        int orderCount=2;

        Long orderId = orderService.order(member.getId(), curBook.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOrder(orderId);

        Item one = itemRepository.findOne(curBook.getId());

        assertEquals("주문 취소시 상태가 취소되어야 합니다.",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("취소한 물건 수 만큼 수량이 증해야합니다.",11,one.getStockQuantity());


    }

    @Test(expected = NotEnoughStockException.class)
    public void overstock() throws Exception{
        //given

        Member member = createMember("mook");
        Book book = createBook("JPA",13000,10);

        int orderCount=11;
        //when
        orderService.order(member.getId(),book.getId(),orderCount);


        //then
        fail("재고 수량 예외처리 발생해야 함 ");


    }


    private Book createBook(String name, int price, int stockQuantity) {

        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("울산","북구","123-456"));
        em.persist(member);

        return member;
    }
}