package com.auction.flab.application.mapper;

import com.auction.flab.application.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberVo> selectMemberByEmail(String email);

    int insertMember(MemberVo memberVo);

}
