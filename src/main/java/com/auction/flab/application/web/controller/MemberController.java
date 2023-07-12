package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.MemberService;
import com.auction.flab.application.web.dto.MemberRequestDto;
import com.auction.flab.application.web.dto.MemberResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponseDto> addProject(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto memberResponseDto = memberService.addMember(memberRequestDto);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
    }

}
