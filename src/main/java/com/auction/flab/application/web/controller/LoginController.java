package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.LoginService;
import com.auction.flab.application.service.TokenService;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginResponseDto;
import com.auction.flab.application.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> createNewAccessToken(@RequestBody LoginRequestDto loginRequestDto) {
        MemberVo memberVo = loginService.checkAccount(loginRequestDto);
        String accessToken = tokenService.createNewAccessToken(memberVo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginResponseDto(accessToken));
    }

}
