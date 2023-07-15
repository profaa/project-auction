package com.auction.flab.application.mapper;

import com.auction.flab.application.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberVo selectMemberByEmail(String email);

    int insertMember(MemberVo memberVo);

}
