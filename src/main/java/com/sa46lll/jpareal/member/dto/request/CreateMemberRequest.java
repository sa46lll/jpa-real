package com.sa46lll.jpareal.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateMemberRequest {

    @NotEmpty
    private String name;
}
