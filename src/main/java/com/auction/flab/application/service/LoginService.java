package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.mapper.MemberMapper;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public MemberVo checkAccount(LoginRequestDto loginRequestDto) {
        MemberVo memberVo = Optional.ofNullable(memberMapper.selectMemberByEmail(loginRequestDto.getEmail()))
                .orElseThrow(() -> new InternalException(ErrorCode.EXCEPTION_ON_LOGIN));
        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), memberVo.getPassword())) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_LOGIN);
        }
        return memberVo;
    }

}
