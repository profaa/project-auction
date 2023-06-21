package com.auction.flab.application.mapper;

import com.auction.flab.application.dto.ProjectDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuctionMapper {

    List<ProjectDto> getProjects();

    int addProject(ProjectDto projectDto);

}
