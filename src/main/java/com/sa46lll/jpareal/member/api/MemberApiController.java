package com.sa46lll.jpareal.member.api;

import com.sa46lll.jpareal.member.domain.Member;
import com.sa46lll.jpareal.member.dto.MemberDto;
import com.sa46lll.jpareal.member.dto.request.CreateMemberRequest;
import com.sa46lll.jpareal.member.dto.response.ResultResponse;
import com.sa46lll.jpareal.member.dto.request.UpdateMemberRequest;
import com.sa46lll.jpareal.member.dto.response.CreateMemberResponse;
import com.sa46lll.jpareal.member.dto.response.UpdateMemberResponse;
import com.sa46lll.jpareal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers(); //모든 값 노출 (지양)
    }

    @GetMapping("/api/v2/members")
    public ResultResponse membersV2() {
        List<Member> members = memberService.findMembers();

        List<MemberDto> collect = members.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new ResultResponse(collect.size(), collect);
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
}
