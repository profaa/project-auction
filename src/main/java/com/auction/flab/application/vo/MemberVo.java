package com.auction.flab.application.vo;

import com.auction.flab.application.web.dto.MemberRequestDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberVo {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String mobileNo;

    public static MemberVo from(MemberRequestDto memberRequestDto) {
        return MemberVo.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .mobileNo(memberRequestDto.getMobileNo())
                .build();
    }

}
