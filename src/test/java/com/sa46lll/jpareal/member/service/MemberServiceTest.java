package com.sa46lll.jpareal.member.service;

import com.sa46lll.jpareal.domain.member.dao.MemberRepository;
import com.sa46lll.jpareal.domain.member.domain.Member;
import com.sa46lll.jpareal.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("sa46lll");

        // when
        Long memberId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test
    public void 중복_회원_예외() throws IllegalStateException {
        // given
        Member member1 = new Member();
        member1.setName("sa46lll");

        Member member2 = new Member();
        member2.setName("sa46lll");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}