package com.auction.flab.application.service;

import com.auction.flab.application.config.jwt.TokenProvider;
import com.auction.flab.application.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;

    public String createNewAccessToken(MemberVo memberVo) {
        return tokenProvider.generateToken(memberVo, Duration.ofHours(2));
    }

}
