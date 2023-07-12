package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberVo checkAccount(LoginRequestDto loginRequestDto) {
        List<MemberVo> members = memberService.getMember(loginRequestDto.getEmail());
        if (isInvalidAccount(members, loginRequestDto.getPassword())) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_LOGIN);
        }
        return members.get(0);
    }

    private boolean isInvalidAccount(List<MemberVo> members, String password) {
        return members == null
                || members.size() != 1
                || !bCryptPasswordEncoder.matches(password, members.get(0).getPassword());
    }

}
