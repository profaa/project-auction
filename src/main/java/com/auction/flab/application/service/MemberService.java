package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.mapper.MemberMapper;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.MemberRequestDto;
import com.auction.flab.application.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public MemberResponseDto addMember(MemberRequestDto memberRequestDto) {
        checkDuplication(memberRequestDto);
        memberRequestDto.setPassword(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()));
        Long memberId = insertMember(memberRequestDto);
        return MemberResponseDto.from(memberId);
    }

    private void checkDuplication(MemberRequestDto memberRequestDto) {
        MemberVo memberVo = memberMapper.selectMemberByEmail(memberRequestDto.getEmail());
        if (memberVo != null) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_INPUT_MEMBER);
        }
    }

    private Long insertMember(MemberRequestDto memberRequestDto) {
        MemberVo memberVo = MemberVo.from(memberRequestDto);
        memberMapper.insertMember(memberVo);
        if (memberVo.getId() == null) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_INPUT_MEMBER);
        }
        return memberVo.getId();
    }

}
