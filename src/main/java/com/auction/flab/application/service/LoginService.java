package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberVo checkAccount(LoginRequestDto loginRequestDto) {
        MemberVo memberVo = Optional.ofNullable(memberService.getMember(loginRequestDto.getEmail()))
                .orElseThrow(() -> new InternalException(ErrorCode.EXCEPTION_ON_LOGIN));
        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), memberVo.getPassword())) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_LOGIN);
        }
        return memberVo;
    }

}
