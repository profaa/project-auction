package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.LoginService;
import com.auction.flab.application.util.TokenUtil;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginRequestDto;
import com.auction.flab.application.web.dto.LoginResponseDto;
import jakarta.validation.Valid;
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
    private final TokenUtil tokenUtil;

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDto> createNewAccessToken(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        MemberVo memberVo = loginService.checkAccount(loginRequestDto);
        String accessToken = tokenUtil.createNewAccessToken(memberVo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginResponseDto(accessToken));
    }

}
