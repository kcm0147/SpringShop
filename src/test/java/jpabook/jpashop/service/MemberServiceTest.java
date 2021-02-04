package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class) // spring이랑 엮어서 실행하기 위해 선
@SpringBootTest
@Transactional // test에서 이것을 붙이면 롤백을 시킴
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false) // 를 넣게되면 DB에 insert문을 날리게된다.
    // Rollback으로 인해 자동적으로 트랙잭션내에 em가 persist를  이루어지기떄문에 insert가 일어나지 않게된다, em.flush()를 하면 query문을 확인할 수 있
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);
        //then
        assertEquals(member,memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class) // 예상되는 예외클래스를 선언한다
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);


        //then
        fail("예외가 발생해야 합니다");


    }



}