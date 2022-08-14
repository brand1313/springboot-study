package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    // JPA 사용을 위해 EntityManager 선언. JpaMemberRepository만을 위해 선언
//    private EntityManager em;

//    @Autowired //JpaMemberRepository만을 위해 선언
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }
    public MemberService memberService() { //spring data jpa를 위해 선언
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {

//        return new MemoryMemberRepository();
//        return new JpaMemberRepository(em);
//    }
}
