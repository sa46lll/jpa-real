package com.sa46lll.jpareal.domain.member.dto.response;

import lombok.Getter;

@Getter
public class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }
}
