package com.auction.flab.application.util;

import com.auction.flab.application.config.jwt.TokenProvider;
import com.auction.flab.application.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class TokenUtil {

    private final TokenProvider tokenProvider;

    public String createNewAccessToken(MemberVo memberVo) {
        return tokenProvider.generateToken(memberVo, Duration.ofHours(2));
    }

}
