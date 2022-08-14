package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //save와 같은 변경 동작에 꼭 필요!!
//@Service
public class MemberService {

    //서비스 생성 때마다 매번 레포지터리 객체를 생성할 것인가?
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 스프링 컨테이너로부터 주입받도록 한다.

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) { //반드시 public 생성자여야 한다.

        //현재 MemoryMemberRepository가 레포지터리 구현체로 있다.
        //따라서 memoryMemberRepository라고 따로 지정할 필요없이 memberRepository로 해도 스프링 컨테이너에서 서비스에 주입해준다.
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){

        // 중복회원 검증
        //방법 1

        // option + command + v : 리턴값을 저절로 만들어준다.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        //방법 2
//        memberRepository.findByName(member.getName()).ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });;
        
        //방법 3

        // ctrl + t를 통해 refactoring 옵션 중 extract를 선택해 함수화 시킨다.
        // 또는 option + cmd + m를 입력하면 바로 extract를 실행한다.
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() { // 서비스 함수의 이름은 실제 서비스와 연관되게 짓는다. repository는 이름을 조금 더 기계적으로 db에 가깝게 짓는다.
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
