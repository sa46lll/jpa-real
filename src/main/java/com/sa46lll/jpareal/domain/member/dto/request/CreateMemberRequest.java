package com.sa46lll.jpareal.domain.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateMemberRequest {

    @NotEmpty
    private String name;
}
