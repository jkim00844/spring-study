package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스
 */
public class AppConfig {

    public MemberService memberService(){
        // MemberServiceImpl은 MemberRepository에 대해 전혀 모름
        // 그냥 MemberServiceImpl의 자기 내부 로직만 알고 있으면 됨
        // 생성자를 통해서 구현체가 뭔지 알게 됨
        // 생성자 주입
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }



}
