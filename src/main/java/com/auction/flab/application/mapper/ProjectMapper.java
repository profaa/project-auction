package com.auction.flab.application.mapper;

import com.auction.flab.application.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {

    int insertProject(ProjectVo projectVo);

}
