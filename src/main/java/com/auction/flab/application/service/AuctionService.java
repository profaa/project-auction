package com.auction.flab.application.service;

import com.auction.flab.application.dto.ProjectDto;

import java.util.List;

public interface AuctionService {

    List<ProjectDto> getProjects();

    void addProject(ProjectDto projectDto);

}
