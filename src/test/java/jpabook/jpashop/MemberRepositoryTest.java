package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) // Junit에게 Spring과 관련된 테스트임을 알려
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // transcational?
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");


        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member); // 같은 트랜잭션내에서 저장을하고 조회하면 같은 엔티티로 식별을 하기 때문에 같다고 인식한다


        //에러가 난다. 엔티티매니저를 통한 데이터변경은 항상 트랜잭션안에서만 이루어져야하기 때문이다 => Transcantional을 추가해줘야한다

        // H2 데이터베이스를 확인해보면 결과가 적용되어있지않다. @transactional 어노테이션이 Test코드에 있으면 DB를 롤백시키기 때문이

        //  @Rollback(false) 어노테이션을 붙인다면 테스트 종료 후 디비에서 데이터를 삭제하지 않는다
        // applicaiton.yaml에 jpadp ddl-auto가 true로 설정이 되어있기 때문에 member 테이블을 새로 자동 생성이된


    }

    @Test
    void save() {
    }

    @Test
    void find() {
    }
}