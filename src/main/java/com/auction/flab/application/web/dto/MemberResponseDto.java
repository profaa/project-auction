package com.auction.flab.application.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberResponseDto {

    private final Long id;

    public static MemberResponseDto from(long id) {
        return new MemberResponseDto(id);
    }

}
