package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa에서의 조회기능에 성능을 최적화 시킨다 // jpa의 모든 서비스는 Transcation 안에서 변경되어야한다.
public class MemberService {

    private final MemberRepository memberRepository; // final을 넣는 것을 권

    @Autowired // 생성자 Injection, 혹은 Lombok 사용, 생성자가 하나라면 생략이 가능하
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }


    //회원 가입

    @Transactional // join은 수정이 되어야하기 때문에 어노테이션을 따로 적어준다
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }


    public void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        //Exception
    }

    //회원 전체 조회

   public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }


}
