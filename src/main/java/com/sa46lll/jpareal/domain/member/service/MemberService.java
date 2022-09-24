package com.sa46lll.jpareal.domain.member.service;

import com.sa46lll.jpareal.domain.member.dao.MemberRepository;
import com.sa46lll.jpareal.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member findOne(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        member.setName("test");

        return member;
    }

    @Transactional
    public void update(Long memberId, String name) {
        Member member = memberRepository.findOne(memberId);
        member.setName(name);
    }
}
